//
//  HistoryViewController.swift
//  Glimpse
//
//  Created by Isabel Higgon on 2023-03-21.
//  Copyright © 2023 Apple. All rights reserved.
//

import Foundation
import UIKit

class HistoryViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.hidesBackButton = true
        NavigationStack.shared.push(self)
    }
    
    @IBAction func backButtonClicked(_ sender: Any) {
        self.performSegue(withIdentifier: "historyToMainScreen", sender: self)
    }
    
       override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
           
           if segue.identifier == "historyToMainScreen" {
               guard let vc = segue.destination as? UIViewController else { return }
           }
       }
}

