//
//  EMMShareKeychain.h
//  EMMSecurity
//
//  Created by clement on 14-5-13.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>

@class EMMKeychainItemWrapper;

typedef enum {
    WriteDataToKeyChain,
    ReadDataFromKeyChain,
    DeleteDataFromKeyChain
}KeyChainOperation;

extern NSString * const kAccessGroup;

@interface EMMShareKeychain : NSObject
{
    @package
    EMMKeychainItemWrapper     *_keychainItemWrapper;
    NSString                *_identifier;
    NSString                *_accessGroup;
    NSString                *_label;
}

- (id)initWithIdentifier:(NSString *)identifier accessGroup:(NSString *)accessGroup;

- (id)initWithIdentifier:(NSString *)identifier accessGroup:(NSString *)accessGroup label:(NSString *)label;

- (BOOL)doKeychainOperation:(KeyChainOperation)operation;

#pragma mark override motheds 
// never call these methods directly yourself,

- (void)didUpdata:(NSData *)data;

- (NSData *)willSaveData;

- (void)didDeleteData;

@end
