//
//  SelectPlaceViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ScreeningCondition.h"

#define ProjectButtonBeginTag        1000
#define ProjectButtonEndTag          1002
#define VideoButtonBeginTag          1010
#define VideoButtonEndTag            1012
#define PhoneButtonBeginTag          1020
#define PhoneButtonEndTag            1022
#define SelectNumberButtonBeginTag   1200
#define SelectNumberButtonEndTag     1204

@class SelectConditionController;

@protocol selectConditionDelegate <NSObject>

@optional
- (void)selectCondition:(SelectConditionController *)selectCondition ScreeningCondition:(ScreeningCondition *)screeningCondition defaultSelected:(int)selectedIndex;

@end

@interface SelectConditionController : UIViewController

@property (weak, nonatomic) IBOutlet UIView *deviceView;

@property (weak, nonatomic) IBOutlet UIView *numberView;

@property (weak, nonatomic) IBOutlet UIButton *deviceButton;

@property (weak, nonatomic) IBOutlet UIButton *storeyButton;

@property (weak, nonatomic) IBOutlet UIButton *numberButton;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *devieceTop;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *numberTop;

@property (nonatomic, weak) id<selectConditionDelegate> delegate;

@property (nonatomic, strong)ScreeningCondition *screenCodition;

@property (nonatomic, assign) int selectedIndex;

@property (nonatomic, copy) NSString *pid;

@end
