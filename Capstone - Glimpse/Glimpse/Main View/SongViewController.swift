//
//  SongViewController.swift
//  Glimpse
//
//  Created by Aly Lin on 2022-12-06.
//  Copyright © 2022 Apple. All rights reserved.
//


import Foundation
import UIKit
import MediaPlayer
import MusicKit
import StoreKit
import CloudKit

struct Response: Codable {
  let message: String
}

class SongViewController: UIViewController {
    
    let userDefaultsSongView = UserDefaults.standard

    @IBOutlet weak var songLabel: UILabel!
    @IBOutlet weak var songView: UIView!
    @IBOutlet weak var artistLabel: UILabel!
    @IBOutlet weak var albumCover: UIImageView!
    @IBOutlet weak var mediaButton: UIButton!
    @IBOutlet weak var likeButton: UIButton!
    @IBOutlet weak var dislikeButton: UIButton!
    @IBOutlet weak var settingsButton: UIButton!
    @IBOutlet weak var historyButton: UIButton!
    @IBOutlet weak var popupCancelButton: UIButton!
    @IBOutlet weak var connectToSpotify: UIButton!
    @IBOutlet weak var disconnectSpotify: UIButton!
    @IBOutlet weak var connectToAppleMusic: UIButton!
    @IBOutlet weak var disconnectAppleMusic: UIButton!
    @IBOutlet weak var progressView: UIProgressView!
    @IBOutlet weak var popupView: UIView!
    @IBOutlet weak var comingSoonView: UIView!
    
    var isPlaying = false
    var hasStartedPlaying = false
    
    let musicPlayer = MPMusicPlayerController.systemMusicPlayer
    let cloudServiceController = SKCloudServiceController()
    let mediaQuery = MPMediaQuery()

    struct Song {
        static var name = ". . ."
        static var artist = ". . ."
        static var codeApple = ""
        static var codeSpotify = ""
        static var userImage = UIImage()
        static var rating = 0 //0 for no interacion, 1 for like, 2 for dislike
        static var linkApple = "https://music.apple.com/ca/album/drivers-license/1560735414?i=1560735424"
        static var linkSpotify = "https://open.spotify.com/track/6U70qxo59BzPBc1vj9xg72?si=q0Aj90wOQ4qj73nnTzm85A&context=spotify%3Aplaylist%3A4Ohhj7iIKrwT26InIO6wOw"
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.hidesBackButton = true
        NavigationStack.shared.push(self)
        getSong()
        //initMediaProgressBar()
    }
    
//    override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
//        if keyPath == "currentPlaybackTime" {
//            let currentPlaybackTime = musicPlayer.currentPlaybackTime
//            let totalPlaybackTime = musicPlayer.nowPlayingItem?.playbackDuration ?? 0
//            let progress = currentPlaybackTime / totalPlaybackTime
//            // Update the progress bar UI here
//        }
//    }
    
    func getSong() {
        
        let dataManager = DataManager.shared
         
        let retrivedSongName = dataManager.SharedSongName
        let retrivedArtistName = dataManager.SharedArtistName
        let retrivedSongIDApple = dataManager.SharedSongIDApple
        let retrivedSongIDSpotify = dataManager.SharedSongIDSpotify
        let retrivedUserImage = dataManager.SharedUserImage
        let retrivedSongLinkApple = dataManager.SharedSongLinkApple
        let retrivedSongLinkSpotify = dataManager.SharedSongLinkSpotify
        
         
        Song.name = retrivedSongName
        Song.artist = retrivedArtistName
        Song.codeApple = retrivedSongIDApple
        Song.codeSpotify = retrivedSongIDSpotify
        Song.userImage = retrivedUserImage
        Song.linkApple = retrivedSongLinkApple
        Song.linkSpotify = retrivedSongLinkSpotify
        
         
      
        modifySongLabels()
        setSongPhoto()
        initMediaProgressBar()
        
        songView.isHidden = false
        popupView.isHidden = true
        comingSoonView.isHidden = true
    }
    
    func modifySongLabels() {
        songLabel.text = Song.name
        artistLabel.text = Song.artist
    }
    
    func setSongPhoto() {
        albumCover.image = Song.userImage
    }
    
    @IBAction func firstmediaToggleCancel() {
        hasStartedPlaying = false
        popupView.isHidden = true
        
    }
    
    @IBAction func continueButton() {
        comingSoonView.isHidden = true
        
    }

    @IBAction func mediaToggle() {
        if (!hasStartedPlaying) {
            hasStartedPlaying = true
            popupView.isHidden = false
        }
            
        else {
            if musicPlayer.playbackState == .playing {
                musicPlayer.pause()
                let newImage = UIImage(systemName: "play.fill")
                mediaButton.setImage(newImage, for: .normal)
                isPlaying = false
            }
            
            else {
                musicPlayer.play()
                let newImage = UIImage(systemName: "pause.fill")
                mediaButton.setImage(newImage, for: .normal)
                isPlaying = true
            }
        }
    }
    
    @IBAction func likeButtonClicked(_ sender: Any) {
        
        var uuidString: String = ""
        var imageString: String = ""
        var songTitleString: String = ""
        var artistString: String = ""
        var spotifyURLString: String = ""
        var appleURLString: String = ""
        var ratingString: String = ""
        
        if (userDefaultsSongView.object(forKey: "uuid") != nil) {
            
            userDefaultsSongView.set("1", forKey: "rating")
            
            if let uuid = userDefaultsSongView.object(forKey: "uuid") as? String {
                uuidString = uuid
                print("userDefaults: " + uuid)
            }
            if let image = userDefaultsSongView.object(forKey: "image") as? String {
                imageString = image
                print("userDefaults: " + image)
            }
            if let song_title = userDefaultsSongView.object(forKey: "song_title") as? String {
                songTitleString = song_title
                print("userDefaults: " + song_title)
            }
            if let artist_name = userDefaultsSongView.object(forKey: "artist_name") as? String {
                artistString = artist_name
                print("userDefaults: " + artist_name)
            }
            if let spotify_URL = userDefaultsSongView.object(forKey: "spotify_URL") as? String {
                spotifyURLString = spotify_URL
                print("userDefaults: " + spotify_URL)
            }
            if let apple_URL = userDefaultsSongView.object(forKey: "apple_URL") as? String {
                appleURLString = apple_URL
                print("userDefaults: " + apple_URL)
            }
            if let rating = userDefaultsSongView.object(forKey: "rating") as? String {
                ratingString = rating
                print("userDefaults: " + rating)
            }
        
            let path = "https://xsthcfo9p1.execute-api.us-east-2.amazonaws.com/default/AddHistoryItem"
            
            var request = URLRequest(url: URL(string: path)!)
            
            let history = ["uuid": uuidString,
                               "image": imageString,
                               "song_title": songTitleString,
                               "artist_name": artistString,
                               "spotify_URL": spotifyURLString,
                               "apple_URL": appleURLString,
                               "rating": ratingString]
            
            guard let jsonData = try? JSONSerialization.data(withJSONObject: history, options: []) else {
                print("Error: Unable to serialize JSON")
                return
            }
            
            request.httpMethod = "POST"
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            request.httpBody = jsonData
            
            let task = URLSession.shared.dataTask(with: request) { data, response, error in
                
                if let error = error {
                    print("Error: \(error.localizedDescription)")
                    return
                }
                
                // Check if there is data in the response
                guard let data = data else {
                    print("Error: No data in response")
                    return
                }
                
                // Decode JSON data
                guard let json = try? JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] else {
                    print("Error: Unable to decode JSON data")
                    return
                }
                
//                print("the response from the lambda function is:")
//                // Print JSON in console
//                print(json)
            }
            task.resume()
        }
        
        //sends like to server to be added to touble of the song match for the user
        Song.rating = 1
    }
        
    @IBAction func dislikeButtonClicked(_ sender: Any) {
        
        print("dislikeButtonClicked")
        
        var uuidString: String = ""
        var imageString: String = ""
        var songTitleString: String = ""
        var artistString: String = ""
        var spotifyURLString: String = ""
        var appleURLString: String = ""
        var ratingString: String = ""
        
        if (userDefaultsSongView.object(forKey: "uuid") != nil) {
            
            userDefaultsSongView.set("0", forKey: "rating")
            
            if let uuid = userDefaultsSongView.object(forKey: "uuid") as? String {
                uuidString = uuid
                print("userDefaults: " + uuid)
            }
            if let image = userDefaultsSongView.object(forKey: "image") as? String {
                imageString = image
                print("userDefaults: " + image)
            }
            if let song_title = userDefaultsSongView.object(forKey: "song_title") as? String {
                songTitleString = song_title
                print("userDefaults: " + song_title)
            }
            if let artist_name = userDefaultsSongView.object(forKey: "artist_name") as? String {
                artistString = artist_name
                print("userDefaults: " + artist_name)
            }
            if let spotify_URL = userDefaultsSongView.object(forKey: "spotify_URL") as? String {
                spotifyURLString = spotify_URL
                print("userDefaults: " + spotify_URL)
            }
            if let apple_URL = userDefaultsSongView.object(forKey: "apple_URL") as? String {
                appleURLString = apple_URL
                print("userDefaults: " + apple_URL)
            }
            if let rating = userDefaultsSongView.object(forKey: "rating") as? String {
                ratingString = rating
                print("userDefaults: " + rating)
            }
        
            let path = "https://xsthcfo9p1.execute-api.us-east-2.amazonaws.com/default/AddHistoryItem"
            
            var request = URLRequest(url: URL(string: path)!)
            
            let history = ["uuid": uuidString,
                               "image": imageString,
                               "song_title": songTitleString,
                               "artist_name": artistString,
                               "spotify_URL": spotifyURLString,
                               "apple_URL": appleURLString,
                               "rating": ratingString]
            
            guard let jsonData = try? JSONSerialization.data(withJSONObject: history, options: []) else {
                print("Error: Unable to serialize JSON")
                return
            }
            
            request.httpMethod = "POST"
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            request.httpBody = jsonData
            
            let task = URLSession.shared.dataTask(with: request) { data, response, error in
                
                if let error = error {
                    print("Error: \(error.localizedDescription)")
                    return
                }
                
                // Check if there is data in the response
                guard let data = data else {
                    print("Error: No data in response")
                    return
                }
                
                // Decode JSON data
                guard let json = try? JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] else {
                    print("Error: Unable to decode JSON data")
                    return
                }
                
                print("the response from the lambda function is:")
                // Print JSON in console
                print(json)
            }
            task.resume()
        }
        //sends dislike to server to be added to touble of the song match for the user
        Song.rating = 0
    }
    
    @IBAction func openInAppleMusic() {
        if (hasStartedPlaying){popupView.isHidden = true}
        
        guard let url = URL(string: Song.linkApple) else {
            return
        }
        if UIApplication.shared.canOpenURL(url) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        }
    }
        
    @IBAction func openInSpotify() {
        if (hasStartedPlaying) {popupView.isHidden = true}
        
        guard let url = URL(string: Song.linkSpotify) else {
            return
        }
        if UIApplication.shared.canOpenURL(url) {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        }

    }
    
    @IBAction func shareButtonTapped() {
        shareToSocialMedia()
    }
    
    func shareToSocialMedia() {
        let shareText = "Check out my song suggestion based on my photo from Glimpse! \n\n" + Song.name + " by " + Song.artist + "\n\nListen on Spotify!\n\n" + Song.linkSpotify
        let shareImage = Song.userImage
        let activityViewController = UIActivityViewController(activityItems: [shareText, shareImage], applicationActivities: nil)
        self.present(activityViewController, animated: true, completion: nil)
    }
    
    func initMediaProgressBar() {
        musicPlayer.addObserver(self, forKeyPath: "currentPlaybackTime", options: .new, context: nil)
    }
    
    @IBAction func settingsButtonClicked(_ sender: Any) {
        self.performSegue(withIdentifier: "songToSettingsScreen", sender: self)
        
    }
    
//    @IBAction func signInButtonPressed(_ sender: Any) {
//        self.performSegue(withIdentifier: "signInScreen", sender: self)
//    }
    
    @IBAction func backButtonClicked(_ sender: Any) {
        self.performSegue(withIdentifier: "songToMainScreen", sender: self)
    }
    
   
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "songToSettingsScreen" {
            guard let vc = segue.destination as? UIViewController else { return }
        }
        
        if segue.identifier == "songToMainScreen" {
            guard let vc = segue.destination as? UIViewController else { return }
        }
     }
    
    @IBAction func functionComingSoon(_ sender: Any) {
        comingSoonView.isHidden = false
                
    }
}


class MPCPlayer {
    private let musicPlayer = MPMusicPlayerController.systemMusicPlayer
    
    func setQueue(with items: [MPMediaItem]) {
        let mediaCollection = MPMediaItemCollection(items: items)
        musicPlayer.setQueue(with: mediaCollection)
    }
    
    func play() {
        musicPlayer.play()
    }
    
    func stop() {
        musicPlayer.stop()
    }
}
