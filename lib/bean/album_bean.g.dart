// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'album_bean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Album _$AlbumFromJson(Map<String, dynamic> json) {
  return Album(
      json['albumname'] as String,
      json['weight'] as String,
      json['artistname'] as String,
      json['resource_type_ext'] as String,
      json['artistpic'] as String,
      json['albumid'] as String);
}

Map<String, dynamic> _$AlbumToJson(Album instance) => <String, dynamic>{
      'albumname': instance.albumname,
      'weight': instance.weight,
      'artistname': instance.artistname,
      'resource_type_ext': instance.resource_type_ext,
      'artistpic': instance.artistpic,
      'albumid': instance.albumid
    };
