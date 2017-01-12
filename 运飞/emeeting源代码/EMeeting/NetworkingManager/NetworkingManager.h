//
//  NetworkingManager.h
//  RedPacket
//
//  Created by itp on 15/10/26.
//  Copyright © 2015年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "EMMHTTPManager.h"
#import "EMMBaseUrlManager.h"
#import "BaseNetworkingObject.h"

#define BaseDataPath    @"baseData"
#define PublicPath    @"public"
#define SharkOffPath  @"sharkOff"
#define MyMeetingPath    @"myMeeting"
#define MeetingScheduledPath    @"meetingScheduled"
#define MeetingBridgePath   @"meetingBridge"
//#define connectPath         @"connect"
#define MeetingDominate     @"meetingDominate"
#define EMeetingLoginNotification  @"EMeetingLoginNotification"

@interface NetworkingManager : NSObject
@property (nonatomic,strong,readonly)EMMHTTPManager *httpManager;
@property (nonatomic,strong,readonly)EMMBaseUrlManager *urlManager;

+ (NetworkingManager *)share;



@end

@interface PageRequestObject : BaseNetworkingObject
@property (nonatomic,copy)NSString *s;
@property (nonatomic,copy)NSString *e;
@property (nonatomic,assign)NSInteger t;
@property (nonatomic,copy)NSString *l;
@property (nonatomic,assign)NSInteger pno;
@property (nonatomic,assign)NSInteger psize;

@end

@interface FunctionObject : BaseNetworkingObject
@property (nonatomic,copy)NSString *k;
@property (nonatomic,copy)NSString *v;

@end

@interface RequestObject : NSObject

@property (nonatomic,copy)NSString *c;
@property (nonatomic,copy)NSString *did;
@property (nonatomic,copy)NSString *t;
@property (nonatomic,copy)NSString *ttp;
@property (nonatomic,copy)NSString *l;
@property (nonatomic,strong)PageRequestObject *p;
@property (nonatomic,strong)NSDictionary *d;
@property (nonatomic,copy)NSString *u;
@property (nonatomic,copy)NSString *ut;
@property (nonatomic,strong)NSArray *f;

- (id)initWithCommandName:(NSString *)commandName;

- (NSDictionary *)requestDictionary;

- (NSString *)soapString;

@end

@interface PageResponseObject : BaseNetworkingObject
@property (nonatomic,strong)NSString *e;
@property (nonatomic,strong)NSString *t;
@property (nonatomic,strong)NSString *u;

@end

@interface ResponseObject : BaseNetworkingObject
@property (nonatomic,assign)BOOL s;
@property (nonatomic,strong)NSString *m;
@property (nonatomic,strong)NSString *c;
@property (nonatomic,strong)id d;
@property (nonatomic,strong)PageResponseObject *p;

@end


