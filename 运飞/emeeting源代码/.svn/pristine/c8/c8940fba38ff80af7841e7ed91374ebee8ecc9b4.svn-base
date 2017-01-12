//
//  FeedbackViewController.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^SuccessBlock)();
@interface FeedbackViewController : UIViewController<UITextViewDelegate,UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextView *proposalText;//建议

@property (weak, nonatomic) IBOutlet UITextField *phoneNumTextField;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *bgViewTopConstraint;

@property (copy, nonatomic) SuccessBlock block;
@end
