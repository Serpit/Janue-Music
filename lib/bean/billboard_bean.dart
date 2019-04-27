import 'package:json_annotation/json_annotation.dart';

part 'billboard_bean.g.dart';

@JsonSerializable()
class Billboard {


  @JsonKey(name: 'billboard_no')
  String billboardNo;

  @JsonKey(name: 'update_date')
  String updateDate;


  @JsonKey(name: 'havemore')
  int havemore;

  @JsonKey(name: 'name')
  String name;

  @JsonKey(name: 'comment')
  String comment;

  @JsonKey(name: 'pic_s192')
  String picS192;

  @JsonKey(name: 'pic_s640')
  String picS640;

  @JsonKey(name: 'pic_s444')
  String picS444;

  @JsonKey(name: 'pic_s260')
  String picS260;

  @JsonKey(name: 'pic_s210')
  String picS210;

  @JsonKey(name: 'web_url')
  String webUrl;

  @JsonKey(name: 'color')
  String color;

  @JsonKey(name: 'bg_color')
  String bgColor;

  @JsonKey(name: 'bg_pic')
  String bgPic;

  Billboard(this.billboardNo,this.updateDate,this.havemore,this.name,this.comment,this.picS192,this.picS640,this.picS444,this.picS260,this.picS210,this.webUrl,this.color,this.bgColor,this.bgPic,);

  factory Billboard.fromJson(Map<String, dynamic> srcJson) => _$BillboardFromJson(srcJson);

  Map<String, dynamic> toJson() => _$BillboardToJson(this);

}