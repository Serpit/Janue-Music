
import 'package:json_annotation/json_annotation.dart';
import 'song_list_bean.dart';
import 'billboard_bean.dart';

part 'music_list_bean.g.dart';
@JsonSerializable()
class MusicListInfo{
  @JsonKey(name:'song_list')
  List<SongListItemInfo> songList;

  @JsonKey(name:'billboard')
  Billboard billboard;

  @JsonKey(name: 'error_code')
  int errorCode;


  MusicListInfo(this.songList, this.billboard, this.errorCode);

  factory MusicListInfo.fromJson(Map<String, dynamic> srcJson) => _$MusicListInfoFromJson(srcJson);

  Map<String, dynamic> toJson() => _$MusicListInfoToJson(this);

}