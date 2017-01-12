//
//  ShakeResultViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/3/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NetworkingManager.h"

@interface ShakeResultViewController : UIViewController

@property (nonatomic, strong) NSArray *dataArr;

@property (nonatomic, strong) PageRequestObject *page;

@property (nonatomic, copy) NSString *timeLong;

@property (nonatomic, copy) NSString *meetingAddressId;

@property (nonatomic, copy) NSString *latitudeAndLongitude;

@property (nonatomic, assign) BOOL isFromFirst;

@end
