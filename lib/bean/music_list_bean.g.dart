// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'music_list_bean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

MusicListInfo _$MusicListInfoFromJson(Map<String, dynamic> json) {
  return MusicListInfo(
      (json['song_list'] as List)
          ?.map((e) => e == null
              ? null
              : SongListItemInfo.fromJson(e as Map<String, dynamic>))
          ?.toList(),
      json['billboard'] == null
          ? null
          : Billboard.fromJson(json['billboard'] as Map<String, dynamic>),
      json['error_code'] as int);
}

Map<String, dynamic> _$MusicListInfoToJson(MusicListInfo instance) =>
    <String, dynamic>{
      'song_list': instance.songList,
      'billboard': instance.billboard,
      'error_code': instance.errorCode
    };
