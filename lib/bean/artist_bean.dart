import 'package:json_annotation/json_annotation.dart';

part 'artist_bean.g.dart';

@JsonSerializable()
class Artist{
  final String yyr_artist;
  final String artistname;
  final String artistid;
  final String artistpic;
  final String weight;

  Artist(this.yyr_artist, this.artistname, this.artistid, this.artistpic,
      this.weight);
  factory Artist.fromJson(Map<String, dynamic> srcJson) => _$ArtistFromJson(srcJson);

  Map<String, dynamic> toJson() => _$ArtistToJson(this);

}