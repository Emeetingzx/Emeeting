//
//  EMMHTTPManager+SOAP.m
//  EmployeeServiceCenter
//
//  Created by clement on 14-8-26.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import "EMMHTTPManager+SOAP.h"
#import "DDXML.h"

static NSString *kTargetNamespace = @"http://RedPacket.AppService.ui.redEnvelope.itp.zte.com";

@implementation EMMHTTPManager (SOAP)

-(NSString *)buildSoapwithMethodName:(NSString *)methodName messageName:(NSString *)messageName paras:(NSDictionary *)parasdic
{
    if (methodName == nil) {
        return nil;
    }
    //根节点
    DDXMLElement *ddRoot = [DDXMLElement elementWithName:@"soap:Envelope"];
    //根节点的命名空间
    [ddRoot addNamespace:[DDXMLNode namespaceWithName:@"soap" stringValue:@"http://schemas.xmlsoap.org/soap/envelope/"]];
    //头
	DDXMLElement *ddHeader = [DDXMLElement elementWithName:@"soap:Header"];
    //body
    DDXMLElement *ddBody = [DDXMLElement elementWithName:@"soap:Body"];
    
    //消息体的命名空间
    DDXMLNode *ddmsgNS = [DDXMLNode namespaceWithName:@"" stringValue:kTargetNamespace];
    DDXMLElement *msg=[DDXMLElement elementWithName:messageName];
    [msg addNamespace:ddmsgNS];
    
    //给消息体添加参数列表，并赋值
    [parasdic enumerateKeysAndObjectsUsingBlock:^(id key, id obj, BOOL *stop) {
        DDXMLElement *paranode=[DDXMLElement elementWithName:key stringValue:obj];
        [msg addChild:paranode];
    }];
    
    [ddBody addChild:msg];
    [ddRoot addChild:ddHeader];
    [ddRoot addChild:ddBody];
    return [ddRoot XMLString];
}

- (NSString *)buildSoapActionWithMethodName:(NSString *)methodName;
{
    if (methodName == nil) {
        return kTargetNamespace;
    }
    NSString *action = [NSString stringWithFormat:@"%@%@",kTargetNamespace,methodName];
    return action;
}

- (NSMutableURLRequest *)requestWithMethod:(NSString *)method path:(NSString *)path jsonString:(NSString *)jsonString soapMethod:(NSString *)soapMethod
{
    NSMutableURLRequest *request = [self requestWithMethod:method path:path parameters:nil];
    [request addValue:[self buildSoapActionWithMethodName:soapMethod] forHTTPHeaderField:@"SOAPAction"];
    [request addValue:@"text/xml;charset=UTF-8" forHTTPHeaderField:@"Content-Type"];
    
    jsonString = [self encryptContent:jsonString request:request];
    NSDictionary *parameters = @{@"jsonString":jsonString};
    NSString *postData = [self buildSoapwithMethodName:soapMethod messageName:soapMethod paras:parameters];
    [request setHTTPBody:[postData dataUsingEncoding:NSUTF8StringEncoding]];
    return request;
}

@end
