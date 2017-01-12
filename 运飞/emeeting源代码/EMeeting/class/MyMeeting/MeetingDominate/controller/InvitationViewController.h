//
//  InvitationViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CustomAlertView.h"
typedef void(^RefreshMeetingJoinInfoBlock)();
@interface InvitationViewController : UIViewController<UITextFieldDelegate,CustomAlertViewDelegate>
@property (weak, nonatomic) IBOutlet UILabel *promptLable;
@property (weak, nonatomic) IBOutlet UILabel *promptFormatLable;
@property (weak, nonatomic) IBOutlet UITextField *numberTextField;

@property (weak, nonatomic) IBOutlet UIButton *addAddressBookBtn;
@property(assign,nonatomic) NSInteger rowNum;
@property (copy, nonatomic) NSString *meetingId;
@property (copy, nonatomic) RefreshMeetingJoinInfoBlock refreshMeetingJoinInfoBlock;
@property(strong,nonatomic) NSArray *meetingJoinInfoList;
@end
