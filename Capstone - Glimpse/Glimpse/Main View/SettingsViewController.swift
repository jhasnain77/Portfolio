//
//  SettingsViewController.swift
//  Glimpse
//
//  Created by Aly Lin on 2023-03-20.
//  Copyright © 2023 Apple. All rights reserved.
//

import Foundation
import UIKit

class SettingsViewController: UIViewController {
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var emailLabel: UILabel!
    @IBOutlet weak var loggedInScreen: UIView!
    @IBOutlet weak var loggedOutButton: UIButton!
    
    let userDefaultsSetting = UserDefaults.standard
    
    
    @IBOutlet weak var comingSoonView: UIView!
    
    @IBAction func functionComingSoon(_ sender: Any) {
        self.comingSoonView.isHidden = false
    }

    @IBAction func continueButton() {
        comingSoonView.isHidden = true

    }

    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.hidesBackButton = true
        NavigationStack.shared.push(self)
        self.comingSoonView.isHidden = true
        if let isLoggedIn = UserDefaults.standard.object(forKey: "loggedIn") as? Bool, isLoggedIn {
            loggedIn()
        }
        else{
            loggedOut()
        }
    }
    
    @IBAction func signUpButtonPressed(_ sender: Any) {
        self.performSegue(withIdentifier: "settingsToSignUpScreen", sender: self)
            
    }
    @IBAction func signInButtonPressed(_ sender: Any) {
        self.performSegue(withIdentifier: "settingsToSignInScreen", sender: self)
            
    }
    @IBAction func loggedOutPressed(_ sender: Any) {
        print("logged out")
        self.performSegue(withIdentifier: "settingsToMainScreenLogOut", sender: self)
        loggedOut()
    }
    
    func loggedIn() {
        self.loggedInScreen.isHidden = false
        if let usernameValue = userDefaultsSetting.object(forKey: "username") as? String {
            usernameLabel.text = usernameValue
        }
    }
    
    @IBAction func backButtonClicked(_ sender: Any) {
        self.performSegue(withIdentifier: "settingsToMainScreen", sender: self)
    }
    
    func loggedOut() {
        self.loggedInScreen.isHidden = true
        userDefaultsSetting.set(false, forKey: "loggedIn")
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "settingsToMainScreen" {
            guard let vc = segue.destination as? UIViewController else { return }
        }
        
        if segue.identifier == "settingsToMainScreenLogOut" {
            guard let vc = segue.destination as? UIViewController else { return }
        }
        
        if segue.identifier == "settingsToSignInScreen" {
            guard let vc = segue.destination as? UIViewController else { return }
        }
        
        if segue.identifier == "settingsToSignUpScreen" {
            guard let vc = segue.destination as? UIViewController else { return }
        }
        
    }
    
}
