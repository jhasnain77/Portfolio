//
//  HistoryTestController.swift
//  Glimpse
//
//  Created by Aly Lin on 2023-03-22.
//  Copyright © 2023 Apple. All rights reserved.
//

import Foundation
import UIKit

class HistoryTestController: UIViewController {
    @IBOutlet weak var mainStackView: UIStackView!
    
    
    
//    @IBOutlet weak var comingSoonView: UIView!
//    @IBAction func functionComingSoon(_ sender: Any) {
//        self.comingSoonView.isHidden = false
//    }
//
//    self.comingSoonView.isHidden = true
//
//    @IBAction func continueButton() {
//        comingSoonView.isHidden = true
//
//    }
    
    
    
    @IBAction func NewSongPressed(_ sender: Any) {
        AddNewSong()
    }
    
    
    func AddNewSong() {
        let myPrefabView = MyPrefabView()
        myPrefabView.title = "My Title"
        myPrefabView.subtitle = "My Subtitle"
        myPrefabView.image = UIImage(named: "Group 3")

        mainStackView.addArrangedSubview(myPrefabView)

    }




}



import UIKit

class MyPrefabView: UIView {
    
    let imageView = UIImageView()
    let labelStackView = UIStackView()
    
    var title: String? {
        didSet {
            titleLabel.text = title
        }
    }
    
    var subtitle: String? {
        didSet {
            subtitleLabel.text = subtitle
        }
    }
    
    var image: UIImage? {
        didSet {
            imageView.image = image
        }
    }
    
    private let titleLabel = UILabel()
    private let subtitleLabel = UILabel()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        imageView.contentMode = .scaleAspectFit
        imageView.translatesAutoresizingMaskIntoConstraints = false
        
        labelStackView.axis = .vertical
        labelStackView.spacing = 8
        labelStackView.translatesAutoresizingMaskIntoConstraints = false
        
        titleLabel.font = UIFont.systemFont(ofSize: 18, weight: .bold)
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        labelStackView.addArrangedSubview(titleLabel)
        
        subtitleLabel.font = UIFont.systemFont(ofSize: 14, weight: .regular)
        subtitleLabel.textColor = .gray
        subtitleLabel.translatesAutoresizingMaskIntoConstraints = false
        labelStackView.addArrangedSubview(subtitleLabel)
        
        addSubview(imageView)
        addSubview(labelStackView)
        
        NSLayoutConstraint.activate([
            imageView.topAnchor.constraint(equalTo: topAnchor),
            imageView.leadingAnchor.constraint(equalTo: leadingAnchor),
            imageView.bottomAnchor.constraint(equalTo: bottomAnchor),
            imageView.widthAnchor.constraint(equalTo: widthAnchor, multiplier: 0.4),
            
            labelStackView.topAnchor.constraint(equalTo: topAnchor),
            labelStackView.leadingAnchor.constraint(equalTo: imageView.trailingAnchor, constant: 8),
            labelStackView.trailingAnchor.constraint(equalTo: trailingAnchor),
            labelStackView.bottomAnchor.constraint(equalTo: bottomAnchor),
            
            
            
        ])

    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
