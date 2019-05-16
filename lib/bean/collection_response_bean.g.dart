// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'collection_response_bean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CollectionResponse _$CollectionResponseFromJson(Map<String, dynamic> json) {
  return CollectionResponse(
      json['state'] as String,
      json['msg'] as String,
      (json['data'] as List)
          ?.map((e) => e == null
              ? null
              : CollectionItem.fromJson(e as Map<String, dynamic>))
          ?.toList());
}

Map<String, dynamic> _$CollectionResponseToJson(CollectionResponse instance) =>
    <String, dynamic>{
      'state': instance.state,
      'msg': instance.msg,
      'data': instance.data
    };

CollectionItem _$CollectionItemFromJson(Map<String, dynamic> json) {
  return CollectionItem(
      json['collectionid'] as int,
      json['songid'] as int,
      json['title'] as String,
      json['artist'] as String,
      json['downloadlink'] as String,
      json['piclink'] as String,
      json['userid'] as int);
}

Map<String, dynamic> _$CollectionItemToJson(CollectionItem instance) =>
    <String, dynamic>{
      'collectionid': instance.collectionid,
      'songid': instance.songid,
      'title': instance.title,
      'artist': instance.artist,
      'downloadlink': instance.downloadlink,
      'piclink': instance.piclink,
      'userid': instance.userid
    };
