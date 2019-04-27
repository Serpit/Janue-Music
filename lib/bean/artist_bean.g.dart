// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'artist_bean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Artist _$ArtistFromJson(Map<String, dynamic> json) {
  return Artist(
      json['yyr_artist'] as String,
      json['artistname'] as String,
      json['artistid'] as String,
      json['artistpic'] as String,
      json['weight'] as String);
}

Map<String, dynamic> _$ArtistToJson(Artist instance) => <String, dynamic>{
      'yyr_artist': instance.yyr_artist,
      'artistname': instance.artistname,
      'artistid': instance.artistid,
      'artistpic': instance.artistpic,
      'weight': instance.weight
    };
