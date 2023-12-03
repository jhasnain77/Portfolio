import boto3
import base64

def lambda_handler(event, context):
    s3 = boto3.client('s3')
    bucket_name = 'glimpse-image-storage-bucket'
    image_name = event['imageName']
    
    # Check if image name ends with '.jpg'
    if not image_name.endswith('.jpg'):
        image_name += '.jpg'
    
    try:
        # Retrieve image from S3 bucket
        response = s3.get_object(Bucket=bucket_name, Key=image_name)
        image_data = response['Body'].read()
        # Convert image to base64
        image_base64 = base64.b64encode(image_data).decode('utf-8')
        return {'statusCode': 200, 'body': image_base64}
    except:
        # Return 'Image not found' if unsuccessful
        return {'statusCode': 404, 'body': 'Image not found'}
