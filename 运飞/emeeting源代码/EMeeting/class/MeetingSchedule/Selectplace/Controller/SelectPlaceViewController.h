//
//  SelectPlaceViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingRoomModel.h"

@class SelectPlaceViewController;

@protocol SelectPlaceViewControllerDelegate <NSObject>

@optional
- (void)selectPlaceViewController:(SelectPlaceViewController *)selectPlace didSelectInfo:(MeetingRoomModel *)selectInfo;

@end

@interface SelectPlaceViewController : UIViewController

@property (nonatomic, weak) id<SelectPlaceViewControllerDelegate> delegate;

@property (nonatomic, strong) MeetingRoomModel *DefaultSelect;

@end
