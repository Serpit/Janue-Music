import 'package:json_annotation/json_annotation.dart';

part 'album_bean.g.dart';
@JsonSerializable()
class Album{
  final String albumname;
  final String weight;
  final String artistname;
  final String resource_type_ext;
  final String artistpic;
  final String albumid;

  Album(this.albumname, this.weight, this.artistname,
      this.resource_type_ext, this.artistpic, this.albumid);

  factory Album.fromJson(Map<String, dynamic> srcJson) => _$AlbumFromJson(srcJson);

  Map<String, dynamic> toJson() => _$AlbumToJson(this);
}