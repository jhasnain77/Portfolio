//
//  DataManager.swift
//  Glimpse
//
//  Created by Isabel Higgon on 2023-03-19.
//  Copyright © 2023 Apple. All rights reserved.
//

import Foundation
import UIKit


class DataManager {
    
    static let shared = DataManager()

    var SharedSongName: String
    var SharedArtistName: String
    var SharedSongIDApple: String
    var SharedSongIDSpotify: String
    var SharedUserImage: UIImage
    var SharedSongLinkApple: String
    var SharedSongLinkSpotify: String
    
    private init() {
        SharedSongName = "defaultname"
        SharedArtistName = "defaultartistname"
        SharedSongIDApple = "203709340"
        SharedSongIDSpotify = "defaultIDSpotify"
        SharedUserImage = UIImage()
        SharedSongLinkApple = "https://music.apple.com/ca/album/drivers-license/1560735414?i=1560735424"
        SharedSongLinkSpotify = "https://open.spotify.com/track/6U70qxo59BzPBc1vj9xg72?si=q0Aj90wOQ4qj73nnTzm85A&context=spotify%3Aplaylist%3A4Ohhj7iIKrwT26InIO6wOw"
        
    }
    
    func updateSharedSongName(NewName: String) {
        SharedSongName = NewName
    }
    
    func updateSharedArtistName(NewArtist: String) {
        SharedArtistName = NewArtist
    }
    
    func updateSharedSongIDSpotify(NewID: String) {
        SharedSongIDSpotify = NewID
    }
    
    func updateSharedSongIDApple(NewID: String) {
        SharedSongIDApple = NewID
    }
    
    func updateSharedUserImage(_ NewImage: UIImage) {
        SharedUserImage = NewImage
    }
    
    func updateSharedLinkApple(NewLink: String) {
        SharedSongLinkApple = NewLink
    }
    
    func updateSharedLinkSpotify(NewLink: String) {
        SharedSongLinkSpotify = NewLink
    }
    
}
