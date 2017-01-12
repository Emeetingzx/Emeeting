//
//  TKAddressBook.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
@interface TKAddressBook : NSObject
@property NSInteger sectionNumber;
@property NSInteger recordID;
@property (nonatomic, copy) NSString *name;
@property (nonatomic, copy) NSString *email;
@property (nonatomic, copy) NSString *tel;
@property (nonatomic, strong) UIImage *image;
@property (nonatomic, assign) BOOL isInvitation;
@end
