// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'base_play_list_bean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BasePlayListItemInfo _$RecentPlayListItemInfoFromJson(
    Map<String, dynamic> json) {
  return BasePlayListItemInfo((json['songid'] as int)?.toInt(),
      json['songname'] as String, json['artist'] as String,json['piclink'] as String);
}

Map<String, dynamic> _$RecentPlayListItemInfoToJson(
        BasePlayListItemInfo instance) =>
    <String, dynamic>{
      'songid': instance.songid,
      'songname': instance.songname,
      'artist': instance.artist,
      'piclink': instance.piclink
    };