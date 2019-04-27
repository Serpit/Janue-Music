// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'song_bean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Song _$SongFromJson(Map<String, dynamic> json) {
  return Song(
      json['bitrate_fee'] as String,
      json['weight'] as String,
      json['songname'] as String,
      json['resource_type'] as String,
      json['songid'] as String,
      json['has_mv'] as String,
      json['yyr_artist'] as String,
      json['resource_type_ext'] as String,
      json['artistname'] as String,
      json['info'] as String,
      json['resource_provider'] as String,
      json['control'] as String,
      json['encrypted_songid'] as String);
}

Map<String, dynamic> _$SongToJson(Song instance) => <String, dynamic>{
      'bitrate_fee': instance.bitrate_fee,
      'weight': instance.weight,
      'songname': instance.songname,
      'resource_type': instance.resource_type,
      'songid': instance.songid,
      'has_mv': instance.has_mv,
      'yyr_artist': instance.yyr_artist,
      'resource_type_ext': instance.resource_type_ext,
      'artistname': instance.artistname,
      'info': instance.info,
      'resource_provider': instance.resource_provider,
      'control': instance.control,
      'encrypted_songid': instance.encrypted_songid
    };
