// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'search_result_bean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

SearchResultBean _$SearchResultBeanFromJson(Map<String, dynamic> json) {
  return SearchResultBean(
      (json['song'] as List)
          ?.map((e) =>
              e == null ? null : Song.fromJson(e as Map<String, dynamic>))
          ?.toList(),
      (json['album'] as List)
          ?.map((e) =>
              e == null ? null : Album.fromJson(e as Map<String, dynamic>))
          ?.toList(),
      (json['artist'] as List)
          ?.map((e) =>
              e == null ? null : Artist.fromJson(e as Map<String, dynamic>))
          ?.toList(),
      json['error_code'] as int,
      json['order'] as String);
}

Map<String, dynamic> _$SearchResultBeanToJson(SearchResultBean instance) =>
    <String, dynamic>{
      'song': instance.song,
      'album': instance.album,
      'artist': instance.artist,
      'error_code': instance.error_code,
      'order': instance.order
    };
