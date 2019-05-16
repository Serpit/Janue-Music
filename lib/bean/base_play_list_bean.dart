

import 'package:json_annotation/json_annotation.dart';

part 'base_play_list_bean.g.dart';

List<BasePlayListItemInfo> getMusicList(List<dynamic> list){
  List<BasePlayListItemInfo> result = [];
  list.forEach((item){
    result.add(BasePlayListItemInfo.fromJson(item));
  });
  return result;
}

@JsonSerializable()
class BasePlayListItemInfo{
  int songid;
  String songname;
  String artist;
  String playlink;
  String ablum;
  BasePlayListItemInfo(this.songid, this.songname, this.artist,this.playlink,this.ablum);

  factory BasePlayListItemInfo.fromJson(Map<String, dynamic> json) => _$RecentPlayListItemInfoFromJson(json);
  Map<String, dynamic> toJson() => _$RecentPlayListItemInfoToJson(this);
}
