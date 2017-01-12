//
//  PhoneOrVideoMeetingViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/1/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

#define NameTextTag          2000
#define PasswordTextTag      2001
#define TypeButtonTag        2010
#define TimeButtonTag        2011
#define LeaderButtonTag      2012

#define SetCenterControllerNoti @"SetCenterControllerNoti"

@interface PhoneOrVideoMeetingViewController : UIViewController

@property (weak, nonatomic) IBOutlet UITextField *nameText;

@property (weak, nonatomic) IBOutlet UITextField *passWordText;

@property (weak, nonatomic) IBOutlet UILabel *typeLabel;

@property (weak, nonatomic) IBOutlet UILabel *timeLabel;

@property (weak, nonatomic) IBOutlet UILabel *leaderLabel;

@property (weak, nonatomic) IBOutlet UILabel *rankLabel;

@property (weak, nonatomic) IBOutlet UIView *bgView;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *bgViewTop;

@end
