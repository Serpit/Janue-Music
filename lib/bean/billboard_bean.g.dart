// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'billboard_bean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Billboard _$BillboardFromJson(Map<String, dynamic> json) {
  return Billboard(
      json['billboard_no'] as String,
      json['update_date'] as String,
      json['havemore'] as int,
      json['name'] as String,
      json['comment'] as String,
      json['pic_s192'] as String,
      json['pic_s640'] as String,
      json['pic_s444'] as String,
      json['pic_s260'] as String,
      json['pic_s210'] as String,
      json['web_url'] as String,
      json['color'] as String,
      json['bg_color'] as String,
      json['bg_pic'] as String);
}

Map<String, dynamic> _$BillboardToJson(Billboard instance) => <String, dynamic>{
      'billboard_no': instance.billboardNo,
      'update_date': instance.updateDate,

      'havemore': instance.havemore,
      'name': instance.name,
      'comment': instance.comment,
      'pic_s192': instance.picS192,
      'pic_s640': instance.picS640,
      'pic_s444': instance.picS444,
      'pic_s260': instance.picS260,
      'pic_s210': instance.picS210,
      'web_url': instance.webUrl,
      'color': instance.color,
      'bg_color': instance.bgColor,
      'bg_pic': instance.bgPic
    };
