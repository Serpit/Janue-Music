import 'package:json_annotation/json_annotation.dart';
import 'album_bean.dart';
import 'song_bean.dart';
import 'artist_bean.dart';

part 'search_result_bean.g.dart';

@JsonSerializable()
class SearchResultBean{
  final List<Song> song;
  final List<Album> album;
  final List<Artist> artist;
  final int error_code;
  final String order;

  SearchResultBean(this.song, this.album, this.artist, this.error_code,
      this.order);

  factory SearchResultBean.fromJson(Map<String, dynamic> srcJson) => _$SearchResultBeanFromJson(srcJson);

  Map<String, dynamic> toJson() => _$SearchResultBeanToJson(this);
}