//
//  ContactsListViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <objc/runtime.h>
#import <AddressBook/AddressBook.h>
#import <AVFoundation/AVFoundation.h>
#import "MeetingDetailsViewController.h"
#import "PhoneOrVideoMeetingDeailsViewController.h"
#import "TKAddressBook.h"
@interface ContactsListViewController : UIViewController<UITableViewDataSource,UITableViewDelegate,AVCaptureMetadataOutputObjectsDelegate>
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (strong,nonatomic)AVCaptureDevice * device;

@property (strong,nonatomic)AVCaptureDeviceInput * input;

@property (strong,nonatomic)AVCaptureMetadataOutput * output;

@property (strong,nonatomic)AVCaptureSession * session;

@property (strong,nonatomic)AVCaptureVideoPreviewLayer * preview;
@property (copy, nonatomic) NSString *meetingId;
@property(strong,nonatomic) NSArray *meetingJoinInfoList;
@property(strong,nonatomic) NSMutableArray *addressBookTemp;
@property (copy, nonatomic) RefreshMeetingJoinInfoBlock refreshMeetingJoinInfoBlock;
@property (copy, nonatomic)TKAddressBook *selectAddressBook;
@end
