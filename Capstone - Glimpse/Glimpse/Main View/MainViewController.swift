import UIKit

class MainViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.hidesBackButton = true
        NavigationStack.shared.push(self)
        startup()
    }
    
    func startup() {
        self.errorView.isHidden = true
        self.comingSoonView.isHidden = true
    }
    
    let userDefaultsMainView = UserDefaults.standard
    
    var firstRun = true

    var hasbeenreturned = false

    
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var startupPrompts: UIView!
    @IBOutlet weak var loadingView: UIView!
    @IBOutlet weak var predictionView: UIView!
    @IBOutlet weak var errorView: UIView!
    @IBOutlet weak var comingSoonView: UIView!
}

extension MainViewController {
    @IBAction func singleTap() {
        guard UIImagePickerController.isSourceTypeAvailable(.camera) else {
            present(photoPicker, animated: false)
            return
        }
        present(cameraPicker, animated: false)
    }
    
    @IBAction func doubleTap() {
        present(photoPicker, animated: false)
    }
    
    @IBAction func serverErrorPopupClosed() {
        self.errorView.isHidden = true
        self.comingSoonView.isHidden = true
    }
    
//    @IBAction func openHistory(_ sender: Any) {
//        if let isLoggedIn = UserDefaults.standard.object(forKey: "loggedIn") as? Bool, isLoggedIn {
//            self.performSegue(withIdentifier: "historyScreen", sender: self)
//        }
//        else {
//            self.performSegue(withIdentifier: "settingsScreen", sender: self)
//        }
//    }
}

extension MainViewController {
    
   func serverError() {
       self.loadingView.isHidden = true
       self.errorView.isHidden = false
    }
    
    func updateImage(_ image: UIImage) {
        DispatchQueue.main.async {
            self.imageView.image = image
        }
        //save image as shareduserimage
        let dataManager = DataManager.shared
        dataManager.updateSharedUserImage(image)
    }

    func userSelectedPhoto(_ photo: UIImage) {
        updateImage(photo)
        if firstRun {
            DispatchQueue.main.async {
                self.firstRun = false
                self.startupPrompts.isHidden = true
                self.loadingView.isHidden = true
                self.predictionView.isHidden = false
                self.errorView.isHidden = true
                self.comingSoonView.isHidden = true
            }
        }
    }
}

extension MainViewController {

    private func sendImageToLambda(_ image: UIImage) {
    
        let strBase64 = image.jpegData(compressionQuality: 0.2)?.base64EncodedString() ?? ""
        
        let path = "https://dh9e4j1lp1.execute-api.us-east-2.amazonaws.com/Dev/GPTFunction"
        
        var request = URLRequest(url: URL(string: path)!)
        
        let payload = ["body": strBase64, "uuid": nil]
        
        guard let jsonData = try? JSONSerialization.data(withJSONObject: payload, options: []) else {
            print("Error: Unable to serialize JSON")
            serverError()
            return
            
        }

        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = jsonData


        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            
            if let error = error {
                print("Error: \(error.localizedDescription)")
                self.serverError()
                return
            }
            
            // Check if there is data in the response
            guard let data = data else {
                print("Error: No data in response")
                self.serverError()
                return
            }
            
            // Decode JSON data
            guard let json = try? JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] else {
                print("Error: Unable to decode JSON data")
                self.serverError()
                return
            }
            
            self.parseLambdaResponse(json)
        }
        task.resume()
      
    }
    
    
    
    private func parseLambdaResponse(_ json: [String: Any]){

        // Get body from JSON
        guard let bodyJson = json["body"] as? [String: Any] else {
            print("Error: Unable to get body from JSON")
            serverError()
            return
        }

        // Extract song title, artist, and song ID from body
        guard let song_artist = bodyJson["song_artist"] as? String else {
            print("Error: Unable to get song_artist from body")
            serverError()
          
            return
        }
        
        // Extract song title, artist, and song ID from body
        guard let song_title = bodyJson["song_title"] as? String else {
            print("Error: Unable to get song_title from body")
            serverError()
           
            return
        }
        
        //Extract song title, artist, and song ID from body
//        guard let apple_song_id = bodyJson["apple_song_id"] as? String else {
//            print("Error: Unable to get apple_song_id from body")
//            serverError()
//            return
//        }
//
//
//
//         // Extract song title, artist, and song ID from body
//         guard let apple_link = bodyJson["apple_link"] as? String else {
//             print("Error: Unable to get apple_link from body")
//                     serverError()
//             return
//         }
//
//
        
        // Extract song title, artist, and song ID from body
        guard let spotify_song_id = bodyJson["spotify_song_id"] as? String else {
            print("Error: Unable to get spotify_song_id from body")
            serverError()

            return
        }
        
        
        // Extract song title, artist, and song ID from body
        guard let spotify_link = bodyJson["spotify_link"] as? String else {
            print("Error: Unable to get spotify_link from body")
            serverError()
            return
        }
        
//        // Extract song title, artist, and song ID from body
//        guard let file_name = bodyJson["file_name"] as? String else {
//            print("Error: Unable to get spotify_link from body")
//            serverError()
//            return
//        }
        
        userDefaultsMainView.set(spotify_link, forKey: "image")
        
        let songTitle = song_title
        let artist = song_artist
        let songIDS = spotify_song_id
        let songLinkS = spotify_link
//        let songLinkA = apple_link
//        let songIDA = apple_song_id

        // Print results
        print("Song Title: \(songTitle)")
        print("Artist: \(artist)")
        print("Song ID Spotify: \(songIDS)")
        print("Song Link Spotify: \(songLinkS)")
        //print("Song ID Apple: \(songIDA)")
        //print("Song Link Apple: \(songLinkA)")
        
        if (userDefaultsMainView.object(forKey: "uuid") != nil) {
            userDefaultsMainView.set(songTitle, forKey: "song_title")
            userDefaultsMainView.set(artist, forKey: "artist_name")
            userDefaultsMainView.set(spotify_link, forKey: "spotify_URL")
            userDefaultsMainView.set("", forKey: "apple_URL")
        }
        
        let dataManager = DataManager.shared

        dataManager.updateSharedSongName(NewName: songTitle)
        dataManager.updateSharedArtistName(NewArtist: artist)
        dataManager.updateSharedSongIDSpotify(NewID: songIDS)
        dataManager.updateSharedLinkSpotify(NewLink: songLinkS)
        //dataManager.updateSharedLinkApple(NewLink: songLinkA)
        //dataManager.updateSharedSongIDApple(NewID: songIDA)
        
        
        self.loadingView.isHidden = true
                
        goToSongView()
    }

    @IBAction func segueButtonPressed(_ sender: Any) {
        self.loadingView.isHidden = false
        let dataManager = DataManager.shared
        let image = dataManager.SharedUserImage

        DispatchQueue.global(qos: .userInitiated).async {
            self.sendImageToLambda(image)
        }
        
    }

    @IBAction func settingsButtonClicked(_ sender: Any) {
        self.performSegue(withIdentifier: "mainToSettingsScreen", sender: self)
        
    }
    
    func goToSongView () {
        DispatchQueue.main.async {
            // Perform the segue
            self.performSegue(withIdentifier: "mainToSongScreen", sender: self)
        }
    }
    
    @IBAction func functionComingSoon(_ sender: Any) {
        self.comingSoonView.isHidden = false
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationVC = segue.destination as? SongViewController {}
                if segue.identifier == "songScreen" {
                    guard let vc = segue.destination as? UIViewController else { return }
                }
    }
}

// Put this piece of code anywhere you like
extension UIViewController {
    func hideKeyboardWhenTappedAround() {
        let tap = UITapGestureRecognizer(target: self, action: #selector(UIViewController.dismissKeyboard))
        tap.cancelsTouchesInView = false
        view.addGestureRecognizer(tap)
    }
    
    @objc func dismissKeyboard() {
        view.endEditing(true)
    }
}
