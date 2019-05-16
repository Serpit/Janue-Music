import 'package:json_annotation/json_annotation.dart';

part 'collection_response_bean.g.dart';

@JsonSerializable()
class CollectionResponse{
  @JsonKey(name: 'state')
  String state;

  @JsonKey(name: 'msg')
  String msg;

  @JsonKey(name: 'data')
  List<CollectionItem> data;

  CollectionResponse(this.state,this.msg,this.data,);

  factory CollectionResponse.fromJson(Map<String, dynamic> srcJson) => _$CollectionResponseFromJson(srcJson);

  Map<String, dynamic> toJson() => _$CollectionResponseToJson(this);
}

@JsonSerializable()
class CollectionItem {
  @JsonKey(name: 'collectionid')
  int collectionid;

  @JsonKey(name: 'songid')
  int songid;

  @JsonKey(name: 'title')
  String title;

  @JsonKey(name: 'artist')
  String artist;

  @JsonKey(name: 'downloadlink')
  String downloadlink;

  @JsonKey(name: 'piclink')
  String piclink;

  @JsonKey(name: 'userid')
  int userid;


  CollectionItem(this.collectionid,this.songid,this.title,this.artist,this.downloadlink,this.piclink,this.userid,);

  factory CollectionItem.fromJson(Map<String, dynamic> srcJson) => _$CollectionItemFromJson(srcJson);

  Map<String, dynamic> toJson() => _$CollectionItemToJson(this);
}