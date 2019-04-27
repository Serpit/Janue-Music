import 'package:json_annotation/json_annotation.dart';

part 'song_bean.g.dart';
@JsonSerializable()
class Song{
  final String bitrate_fee;
  final String weight;
  final String songname;
  final String resource_type;
  final String songid;
  final String has_mv;
  final String yyr_artist;
  final String resource_type_ext;
  final String artistname;
  final String info;
  final String resource_provider;
  final String control;
  final String encrypted_songid;

  Song(this.bitrate_fee, this.weight, this.songname, this.resource_type,
      this.songid, this.has_mv, this.yyr_artist, this.resource_type_ext,
      this.artistname, this.info, this.resource_provider, this.control,
      this.encrypted_songid);

  factory Song.fromJson(Map<String, dynamic> srcJson) => _$SongFromJson(srcJson);

  Map<String, dynamic> toJson() => _$SongToJson(this);

}