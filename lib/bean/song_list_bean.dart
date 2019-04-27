import 'package:json_annotation/json_annotation.dart';

part 'song_list_bean.g.dart';

@JsonSerializable()
class SongListItemInfo{
  @JsonKey(name: 'artist_id')
  String artistId;

  @JsonKey(name: 'language')
  String language;

  @JsonKey(name: 'pic_big')
  String picBig;

  @JsonKey(name: 'pic_small')
  String picSmall;

  @JsonKey(name: 'country')
  String country;

  @JsonKey(name: 'area')
  String area;

  @JsonKey(name: 'publishtime')
  String publishtime;

  @JsonKey(name: 'album_no')
  String albumNo;

  @JsonKey(name: 'lrclink')
  String lrclink;

  @JsonKey(name: 'copy_type')
  String copyType;

  @JsonKey(name: 'hot')
  String hot;

  @JsonKey(name: 'all_artist_ting_uid')
  String allArtistTingUid;

  @JsonKey(name: 'resource_type')
  String resourceType;

  @JsonKey(name: 'is_new')
  String isNew;

  @JsonKey(name: 'rank_change')
  String rankChange;

  @JsonKey(name: 'rank')
  String rank;

  @JsonKey(name: 'all_artist_id')
  String allArtistId;

  @JsonKey(name: 'style')
  String style;

  @JsonKey(name: 'del_status')
  String delStatus;

  @JsonKey(name: 'relate_status')
  String relateStatus;

  @JsonKey(name: 'toneid')
  String toneid;

  @JsonKey(name: 'all_rate')
  String allRate;



  @JsonKey(name: 'has_mv_mobile')
  int hasMvMobile;

  @JsonKey(name: 'versions')
  String versions;

  @JsonKey(name: 'bitrate_fee')
  String bitrateFee;

  @JsonKey(name: 'biaoshi')
  String biaoshi;

  @JsonKey(name: 'info')
  String info;

  @JsonKey(name: 'has_filmtv')
  String hasFilmtv;

  @JsonKey(name: 'si_proxycompany')
  String siProxycompany;

  @JsonKey(name: 'res_encryption_flag')
  String resEncryptionFlag;

  @JsonKey(name: 'song_id')
  String songId;

  @JsonKey(name: 'title')
  String title;

  @JsonKey(name: 'ting_uid')
  String tingUid;

  @JsonKey(name: 'author')
  String author;

  @JsonKey(name: 'album_id')
  String albumId;

  @JsonKey(name: 'album_title')
  String albumTitle;

  @JsonKey(name: 'is_first_publish')
  int isFirstPublish;

  @JsonKey(name: 'havehigh')
  int havehigh;

  @JsonKey(name: 'charge')
  int charge;

  @JsonKey(name: 'has_mv')
  int hasMv;

  @JsonKey(name: 'learn')
  int learn;

  @JsonKey(name: 'song_source')
  String songSource;

  @JsonKey(name: 'piao_id')
  String piaoId;

  @JsonKey(name: 'korean_bb_song')
  String koreanBbSong;

  @JsonKey(name: 'resource_type_ext')
  String resourceTypeExt;

  @JsonKey(name: 'mv_provider')
  String mvProvider;

  @JsonKey(name: 'artist_name')
  String artistName;

  @JsonKey(name: 'pic_radio')
  String picRadio;

  @JsonKey(name: 'pic_s500')
  String picS500;

  @JsonKey(name: 'pic_premium')
  String picPremium;

  @JsonKey(name: 'pic_huge')
  String picHuge;

  @JsonKey(name: 'album_500_500')
  String album500500;

  @JsonKey(name: 'album_800_800')
  String album800800;

  @JsonKey(name: 'album_1000_1000')
  String album10001000;

  SongListItemInfo(this.artistId,this.language,this.picBig,this.picSmall,this.country,this.area,this.publishtime,this.albumNo,this.lrclink,this.copyType,this.hot,this.allArtistTingUid,this.resourceType,this.isNew,this.rankChange,this.rank,this.allArtistId,this.style,this.delStatus,this.relateStatus,this.toneid,this.allRate,this.hasMvMobile,this.versions,this.bitrateFee,this.biaoshi,this.info,this.hasFilmtv,this.siProxycompany,this.resEncryptionFlag,this.songId,this.title,this.tingUid,this.author,this.albumId,this.albumTitle,this.isFirstPublish,this.havehigh,this.charge,this.hasMv,this.learn,this.songSource,this.piaoId,this.koreanBbSong,this.resourceTypeExt,this.mvProvider,this.artistName,this.picRadio,this.picS500,this.picPremium,this.picHuge,this.album500500,this.album800800,this.album10001000,);

  factory SongListItemInfo.fromJson(Map<String, dynamic> srcJson) => _$SongListItemInfoFromJson(srcJson);

  Map<String, dynamic> toJson() => _$SongListItemInfoToJson(this);
}