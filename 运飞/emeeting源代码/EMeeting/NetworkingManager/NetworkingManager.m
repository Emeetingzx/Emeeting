//
//  NetworkingManager.m
//  RedPacket
//
//  Created by itp on 15/10/26.
//  Copyright © 2015年 itp. All rights reserved.
//

#import "NetworkingManager.h"
#import "EMMNetworking.h"
#import "EMMSecurity.h"
#import "MBProgressHUD.h"
#import "AppDelegate.h"

@interface NetworkingManager ()
{
    EMMBaseUrlManager   *_urlManager;
    EMMHTTPManager *_httpManager;
    EMMNetworkStatus *_networkStatus;
}
@end

@implementation NetworkingManager
@synthesize httpManager = _httpManager;
@synthesize urlManager = _urlManager;

+ (NetworkingManager *)share
{
    static NetworkingManager *manager = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        manager = [[self alloc] init];
    });
    return manager;
}

- (id)init
{
    self = [super init];
    if (self)
    {
        _urlManager = [[EMMBaseUrlManager alloc] initWithServer:@"EMEETING"];
        _httpManager = [[EMMHTTPManager alloc] initWithInternetBaseURL:[NSURL URLWithString:[_urlManager internetServerBaseUrl]]
                                                       intranetBaseUrl:[NSURL URLWithString:[_urlManager intranetServerBaseUrl]]];
        _networkStatus = [EMMNetworkStatus networkStatuWithHostname:[_urlManager intranetServerHost]];
        [self networkchange];
        [_networkStatus startNotifier];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(networkchange) name:kEMMReachabilityChangedNotification object:nil];
    }
    return self;
}

- (void)networkchange
{
    if ([_networkStatus isReachableViaWiFi])
    {
        [EMMNetworkSetting share].networking = EMMNetworking_Internet;
    }
    else if ([_networkStatus isReachableViaWWAN])
    {
        [EMMNetworkSetting share].networking = EMMNetworking_Internet;
    }
    else
    {
        dispatch_async(dispatch_get_main_queue(), ^{
            AppDelegate *delegate = [UIApplication sharedApplication].delegate;
            MBProgressHUD *resultHUD = [MBProgressHUD showHUDAddedTo:delegate.window animated:YES];
            resultHUD.mode = MBProgressHUDModeText;
            resultHUD.labelText = @"当前网络不可用";
            [resultHUD hide:YES afterDelay:1.0];
            NSLog(@"当前网络不可用");
        });
    }
}

- (void)checkNetworking
{
    NSString *path = [_urlManager path:BaseDataPath];
    NSString *baseUrl = [_urlManager intranetServerBaseUrl];
    NSString *urlString = [NSString stringWithFormat:@"%@%@",baseUrl,path];
    NSURL *url = [NSURL URLWithString:urlString];
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:url
                                                                cachePolicy:NSURLRequestReloadIgnoringLocalCacheData
                                                            timeoutInterval:10];
    NSError *error = nil;
    NSData *data = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:&error];
    if (data)
    {
        [EMMNetworkSetting share].networking = EMMNetworking_Intranet;
        dispatch_async(dispatch_get_main_queue(), ^{
            AppDelegate *delegate = [UIApplication sharedApplication].delegate;
            MBProgressHUD *resultHUD = [MBProgressHUD showHUDAddedTo:delegate.window animated:YES];
            resultHUD.mode = MBProgressHUDModeText;
            resultHUD.labelText = @"已切换到内网";
            [resultHUD hide:YES afterDelay:1.0];
            NSLog(@"已切换到内网");
        });
    }
    else
    {
        [EMMNetworkSetting share].networking = EMMNetworking_Internet;
        dispatch_async(dispatch_get_main_queue(), ^{
            AppDelegate *delegate = [UIApplication sharedApplication].delegate;
            MBProgressHUD *resultHUD = [MBProgressHUD showHUDAddedTo:delegate.window animated:YES];
            resultHUD.mode = MBProgressHUDModeText;
            resultHUD.labelText = @"已切换到外网";
            [resultHUD hide:YES afterDelay:1.0];
            NSLog(@"已切换到外网");
        });
    }
}

@end

@implementation PageRequestObject
@synthesize s = _s;
@synthesize e = _e;
@synthesize t = _t;
@synthesize l = _l;
@synthesize pno = _pno;
@synthesize psize = _psize;

- (id)init
{
    self = [super init];
    if (self) {
        _t = 0;
        _pno = 1;
        _psize = 10;
    }
    return self;
}

- (NSDictionary *)networkingDictionary
{
    return @{@"S":self.s ?:NULLObject,
             @"E":self.e ?:NULLObject,
             @"T":@(self.t),
             @"L":self.l ?:NULLObject,
             @"PNO":@(self.pno),
             @"PSIZE":@(self.psize)};
}

@end

@implementation FunctionObject
@synthesize k = _k;
@synthesize v = _v;

- (NSDictionary *)networkingDictionary
{
    return @{@"K":self.k ?:NULLObject,@"V":self.v ?:NULLObject};
//    return @{@"K":self.k ?:NULLObject,@"V":@"113.928184,22.518128"};
}


@end


@implementation RequestObject
@synthesize c = _c;
@synthesize did = _did;
@synthesize t = _t;
@synthesize ttp = _ttp;
@synthesize l = _l;
@synthesize p = _p;
@synthesize d = _d;
@synthesize u = _u;
@synthesize ut = _ut;
@synthesize f = _f;

- (id)initWithCommandName:(NSString *)commandName
{
    self = [super init];
    if (self) {
        _c = commandName;
        _did = [EMMSecurity share].deviceId;
        _t = [EMMSecurity share].token;
        _ttp = [(AppDelegate*)[[UIApplication sharedApplication]delegate] tokenType];
        _l = @"2052";
        //_ut = [EMMSecurity share].userId;
        _u = [EMMSecurity share].userId;
        _p = [[PageRequestObject alloc] init];
    }
    return self;
}

- (id)init
{
    return [self initWithCommandName:@""];
}

- (NSDictionary *)requestDictionary
{
    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    [dict setObject:self.c forKey:@"C"];
    [dict setObject:self.did forKey:@"DId"];
//    [dict setObject:self.t ?:NULLObject forKey:@"T"];
//    [dict setObject:self.ttp ?:NULLObject forKey:@"TTP"];
    [dict setObject:self.l forKey:@"L"];
//    [dict setObject:self.u ?:NULLObject forKey:@"U"];
//    [dict setObject:self.ut ?:NULLObject forKey:@"UT"];
//    [dict setObject:self.p ? [self.p networkingDictionary]:[NSNull null] forKey:@"P"];
    [dict setObject:@"admin" forKey:@"U"];
    [dict setObject:@[] forKey:@"lstAttachment"];
    if (self.d)
    {
        NSData * jsonData = [NSJSONSerialization dataWithJSONObject:self.d options:NSJSONWritingPrettyPrinted error:nil];
        NSString *dString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        [dict setObject:dString forKey:@"D"];
    }
    else
    {
//        [dict setObject:[NSNull null] forKey:@"D"];
    }
    [dict setObject:self.f ? [self.f networkingArray]:[NSNull null] forKey:@"F"];
    return dict;
}

- (NSString *)soapString
{
    NSDictionary *dict = [self requestDictionary];
    NSData * jsonData = [NSJSONSerialization dataWithJSONObject:dict options:NSJSONWritingPrettyPrinted error:nil];
    return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
}

@end

@implementation PageResponseObject
@synthesize e = _e;
@synthesize t = _t;
@synthesize u = _u;

- (id)initWithDictionary:(NSDictionary *)dict
{
    self = [super init];
    if (self)
    {
        _e = dict[@"E"]!=NULLObject?dict[@"E"]:@"";
        _t = dict[@"T"]!=NULLObject?dict[@"T"]:@"";
        _u = dict[@"U"]!=NULLObject?dict[@"U"]:@"";
    }
    return self;
}

@end

@implementation ResponseObject
@synthesize s = _s;
@synthesize m = _m;
@synthesize c = _c;
@synthesize d = _d;
@synthesize p = _p;

- (id)initWithDictionary:(NSDictionary *)dict
{
    self = [super initWithDictionary:dict];
    if (self)
    {
        _s = [dict[@"S"] boolValue];
        _m = dict[@"M"];
        _c = dict[@"C"];
        _d = dict[@"D"]?:NULLObject;
        NSDictionary *pDict = dict[@"P"];
        if ([pDict isKindOfClass:[NSDictionary class]])
        {
            _p = [[PageResponseObject alloc] initWithDictionary:pDict];
        }
    }
    return self;
}

@end


