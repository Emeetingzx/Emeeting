//
//  EMMCryptoConfig.h
//  EMMNetworking
//
//  Created by itp on 15/8/11.
//  Copyright (c) 2015年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "EMMCryptoDelegate.h"


@interface EMMCryptoNote : NSObject
@property (nonatomic,strong) NSString *channel;
@property (nonatomic,strong) NSString *cryptoAlgo;
@property (nonatomic,strong) NSString *appPrivateKey;
@property (nonatomic,strong) NSString *appPublicKey;
@property (nonatomic,strong) NSString *serverPublicKey;

- (void)decodeCryptoContent:(NSString *)cryptoContent;

- (EMMCryptoType)cryptoType;

@end

@interface EMMCryptoConfig : NSObject

+ (instancetype)share;

//判断url请求是否有加密，如果有返回对应的加密对象，没有返回nil
- (EMMCryptoNote *)cryptoNoteForUrlPath:(NSString *)urlPath;

@end
