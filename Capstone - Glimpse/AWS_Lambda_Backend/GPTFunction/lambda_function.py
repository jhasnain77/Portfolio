import json
import sys
import os
import uuid
import base64

# Get the absolute path to the directory containing the Lambda function code
function_path = os.path.dirname(os.path.abspath(__file__))

# Construct the path to the venv directory
venv_path = os.path.join(function_path, 'venv/Lib/site-packages')

# Add the venv directory to the system path
sys.path.insert(0, venv_path)

# Import the openai module
import openai
import boto3
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

openai.api_key = "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"

dynamodb = boto3.resource('dynamodb')
preferences_table = dynamodb.Table('glimpse-user-preferences')

def lambda_handler(event, context):

    # Set up credentials
    client_id = '838c9779e26f425ca76f1f88aba7835a'
    client_secret = '810cbe2e6dd04627bb8071dbff0a983d'
    client_credentials_manager = SpotifyClientCredentials(client_id=client_id, client_secret=client_secret)
    sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

    s3 = boto3.client('s3')
    rekognition = boto3.client('rekognition')

    genres = 'pop, rock, jazz, classical'

    if (event['uuid'] != None):
        user_id = event['uuid']
        preferences = preferences_table.get_item(Key={'UUID': user_id})
        genres = preferences['Item'].get('genres', None)

    file_content = base64.b64decode(event['body'])
    file_name = str(uuid.uuid4()) + '.jpg'  # generates a random filename with a .jpg extension
    bucket_name = 'glimpse-image-storage-bucket'
    
    s3.put_object(Body=file_content, Bucket=bucket_name, Key=file_name)
    
    response = rekognition.detect_labels(
        Image={
            'S3Object': {
                'Bucket': bucket_name,
                'Name': file_name
            }
        },
        MaxLabels=3
    )
    
    tags = [label['Name'].lower() for label in response['Labels']]
    
    input_text = ", ".join(tags)
    print(input_text)
    print(genres)

    # Generate a UUID for the message
    message_uuid = str(uuid.uuid4())

    # Call the OpenAI API with the metadata parameter set to include the UUID
    response = openai.Completion.create(
        engine="text-davinci-003",
        prompt=my_function(input_text, genres, message_uuid),
        max_tokens=256,
        temperature=0.7,
        n=1,
        stop=None,
    )

    # Extract the output text from the API response
    output_text = response.choices[0].text.strip()

    print(output_text)

    # Create a message object with the response from OpenAI API
    message = {
        'message': output_text,
        'uuid': message_uuid
    }

    # Store the message in the database
    messages_table = dynamodb.Table('glimpse-messages')
    messages_table.put_item(Item=message)
    
    # Split the input string into a list using the '-' separator
    split_list = output_text.split('-')

    # Extract the required values from the split list
    song_title = split_list[0].strip()[1:-1]
    song_artist = split_list[1].strip().replace("[", "").replace("]", "")

    # Search for a track
    query = f'{song_title} by {song_artist}'
    print(query)
    result = sp.search(query, type='track', limit=1)

    # Get the track ID
    spotify_song_id = result['tracks']['items'][0]['id']
    print(spotify_song_id)

    # Get the track URL
    spotify_link = result['tracks']['items'][0]['external_urls']['spotify']
    print(spotify_link)
    # apple_url = split_list[3].strip()
    # apple_link = split_list[5].strip()

    # # Extract the song IDs from the URLs
    # apple_song_id = apple_url[apple_url.find('=') + 1:]
    
    return {
        'statusCode': 200,
        'headers': {
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token',
            'Access-Control-Allow-Credentials': 'true',
            'Content-Type': 'application/json'
        },
        'body': {
            'output': output_text,
            'file_name': file_name,
            'song_title': song_title,
            'song_artist': song_artist,
            'spotify_song_id': spotify_song_id,
            'apple_song_id': None,
            'spotify_link': spotify_link,
            'apple_link': None,
            'message_uuid': message_uuid
        }
    }


def my_function(list_of_tags, genres, message_uuid):

    input_text = list_of_tags

    tags = input_text.split(", ")
    tag1, tag2, tag3 = tags

    prompt = f"Give an existing song name and singer based on these keywords: {tag1}, {tag2}, {tag3}. I would like it to be in one of the following genres: {genres}. It doesn't have to include the keywords in the song lyrics or title, it just has to have some correlation. Make sure that the song is available in both Spotify and in Apple music. Give me a different one than your previous answer. Give me an answer in the following format and no other explanations: [\"Song name\"] - [Singer] - [Genre] (uuid:{message_uuid})"
    # prompt = "I would like you to give ALL responses in the form of (excluding the square brackets): [\"Song name\"] - [Singer] - [Genre] - (uuid:{message_uuid})"
    print(prompt)

    return prompt


# event = {"Payload": "{\"input\": \"engineering, studying, programming\"}"}

# print(lambda_handler(event, None))

# with open("Okayu Sketch.jpg", "rb") as image_file:
#     encoded_string = base64.b64encode(image_file.read())

# event = {"body": encoded_string,
#         'uuid': None}

# print(lambda_handler(event, None))