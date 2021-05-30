from flask import Flask, request, jsonify
import tensorflow as tf
from tensorflow import keras
from skimage import transform, io
import numpy as np
  
app = Flask(__name__)

@app.route('/api/predict', methods=['POST'])
def recognize_image():
   if 'file' not in request.files:
        resp = jsonify({'message': 'No image in the request'})
        resp.status_code = 400
        return resp
    files = request.files.getlist('file')

    img_url = files
    
    # prepare image for prediction
    img_array = io.imread(img_url, as_gray=False)
    small_grey = transform.resize(img_array, (300,300), mode='symmetric', preserve_range=True) 
    img_to_predict = np.expand_dims(small_grey / 255.0, 0)
    
    # predict
    prediction_array = model.predict(img_to_predict)
        
    # prepare api response
    class_names = ['T-shirt/top', 'Trouser', 'Pullover', 'Dress', 'Coat', 'Sandal', 'Shirt', 'Sneaker', 'Bag', 'Ankle boot']
    result = {
        "prediction" : class_names[np.argmax(prediction_array)],
        "confidence" : '{:2.0f}%'.format(100*np.max(prediction_array))
    }
    
    return jsonify(isError= False, message= "Success", statusCode= 200, data=result), 200


if __name__ == '__main__':
    model = keras.experimental.load_from_saved_model('fashion_mnist_classifier')
    app.run(debug=True, host='0.0.0.0')