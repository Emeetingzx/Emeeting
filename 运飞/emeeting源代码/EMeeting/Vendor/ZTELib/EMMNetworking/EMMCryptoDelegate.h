//
//  EMMCryptoDelegate.h
//  EMMNetworking
//
//  Created by itp on 15/8/6.
//  Copyright (c) 2015年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef enum {
    EMMCrypto_None = 0,
    EMMCrypto_DES,
    EMMCrypto_Rabin
} EMMCryptoType;

@protocol EMMCryptoDelegate <NSObject>

+ (NSString *) encrypt:(NSString *)srcStr publicKey:(NSString *) publicKey;

+ (NSString *) decrypt:(NSString *) decStr privateKey:(NSString *) privateKey;

@end
