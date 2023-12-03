# Glimpse

Welcome to our capstone project, Glimpse! This app was created by 4 students from the University of Western Ontario's Software Engineering graduating class of 2023. With this application, you can take a picture of ANYTHING and find a song that corresponds to that image!

## Getting Started

Currently, the app is under review to be released on the App Store, however in the meantime, you can try this app if you have a Mac, XCode and an iPhone. Just download the code, open it in XCode, click build and run with your device connected and you'll be able to run it! Android support is in the works.

## Usage

Once you have the app on your device, you can start finding songs for your photos right away! Snap a picture or upload one from your library and click "I'm Ready!" Give the AI a little time to pick a song and once it finds one, it'll give you the link to play it in Spotify (Apple Music support in the works). You can play or pause from within the app itself and see how the song matches up to your photo!

If you want to give a rating as to whether or not the song recommendation fit, you will need to make an account. Click the gear icon to create an account and login to the app. Users who are logged in get access to additional functions, including giving ratings, viewing a full history of all the songs they've gotten and their associated images, and the ability to set preferences for genres that they prefer to hear.

## How It Works

The app's primary functions of image recognition and song recommendation are driven by Amazon Rekognition and OpenAI GPT-3. The user's image is sent to an AWS Lambda function that decodes that image from Base64 and feed's it to the Amazon Rekognition service. The top 3 tags that are associated with that image are then read and put into a specialized query that is sent to GPT-3. The response is then recorded and the song recommendation is searched for on Spotify (and soon Apple Music). Then all the information is returned in the response to the POST request when this Lambda function is invoked.

The recommendations returned by GPT-3 can be somewhat personalized by users who are logged in. The primary personalization is genre. The user can submit any number of genres in list form that they would like the AI to pick from. By default, the AI will return songs in the pop, rock, classical and jazz genres. Logged in users will be able to give feedback on whether or not the song was a good recommendation. The AI will learn from this and adjust it's responses accordingly. Users who aren't logged in however will not be able to give feedback, but will be communicating with the same instance of the AI that is learning from logged in users.

The account creation is very standard. When an account is made, email and username must be unique, and there are password restrictions (capital letter, number, special character, etc). There is a user table that is updated every time a user is created, and the password is hashed when stored there. Additionally, there is also a table created for that user in the format of '[UUID]-history' that will store the full history of that user's songs and images.

When a user gives a review for a suggestion being good or bad, that review is stored in their unique history table. That rating is also sent to the AI to let it know if it's recommendation was good.

Most of the app's functionalities come from sending POST requests to AWS API Gateways that communicate with the lambda functions that are set up for each use. In the "Backend" folder, all the lambda functions can be seen.

## Acknowledgements

- AWS:
  - Lambda
  - API Gateway
  - Amazon Rekognition
  - DynamoDB
  - IAM
  - S3
- OpenAI API GPT-3
- Spotify API
- Apple Developers
- Apple Icons

## Copyright

© Jarrod Hasnain, Isabel Higgon, Stella Lee, Alexandria Lin, 2023.
