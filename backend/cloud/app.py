from flask import Flask, request, jsonify
import tensorflow as tf
from tensorflow import keras
from skimage import transform, io
import numpy as np
import os
from keras.models import load_model
from PIL import Image
from datetime import datetime

app = Flask(__name__)

UPLOAD_FOLDER = 'static/uploads/'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif', 'tiff', 'webp'}


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


def auc(y_true, y_pred):
    auc = tf.metrics.auc(y_true, y_pred)[1]
    keras.backend.get_session().run(tf.local_variables_initializer())
    return auc


@app.route('/')
def main():
    return 'This is Radiant Team API for predict skin disease'


@app.route('/api/predict', methods=['POST'])
def recognize_image():
    if 'file' not in request.files:
        resp = jsonify({'message': 'No image in the request'})
        resp.status_code = 400
        return resp
    files = request.files.getlist('file')
    filename = "predict_image.png"
    for file in files:
        if file and allowed_file(file.filename):
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            success = True
        else:
            errors["message"] = 'File type of {} is not allowed'.format(file.filename)
    img_url = os.path.join(app.config['UPLOAD_FOLDER'], filename)

    # convert image to RGB
    img = Image.open(img_url).convert('RGB')
    now = datetime.now()
    image_predict = 'static/uploads/' + now.strftime("%H%M%S") + ".png"
    img.convert('RGB').save(image_predict, format="png")
    img.close()

    # load model for prediction
    model = load_model('dermnet.h5', custom_objects={'auc': auc})

    # prepare image for prediction
    img_array = io.imread(image_predict, as_gray=False)
    small_grey = transform.resize(img_array, (300, 300), mode='symmetric', preserve_range=True)
    img_to_predict = np.expand_dims(small_grey / 255.0, 0)

    # predict
    prediction_array = model.predict(img_to_predict)

    # prepare api response
    class_names = ['Acne and Rosacea Photos', 'Actinic Keratosis Basal Cell Carcinoma and other Malignant Lesions', 'Light Diseases and Disorders of Pigmentation', 'Vascular Tumors', 'Warts Molluscum and other Viral Infections']
    result = {
        "prediction": class_names[np.argmax(prediction_array)],
        "confidence": '{:2.0f}%'.format(100 * np.max(prediction_array))
    }

    return jsonify(isError=False, message="Success", statusCode=200, data=result), 200


if __name__ == '__main__':
    app.run(debug=False, host='0.0.0.0')
