//
//  EMMCryptoDes.h
//  EMMNetworking
//
//  Created by itp on 15/8/5.
//  Copyright (c) 2015年 zte. All rights re *served.
//

#import <Foundation/Foundation.h>
#import "EMMCryptoDelegate.h"

@interface EMMCryptoDes : NSObject<EMMCryptoDelegate>

+ (NSString *) defaultEncrypt:(NSString *)srcStr;

+ (NSString *) defaultDecrypt:(NSString *) decStr;

@end
