import 'dart:async';

import 'package:flutter/material.dart';
import 'package:provide/provide.dart';
import 'package:rxdart/rxdart.dart';

abstract class Presenter{
  //处理点击事件

  void onClick(String action);
}

abstract class ItemPresenter<T> {
  void onItemClick(String action,T item);
}


class BaseProvide with ChangeNotifier{
  CompositeSubscription compositeSubscription = CompositeSubscription();

  addSubscription(StreamSubscription subscription){
    compositeSubscription.add(subscription);
  }

  @override
  void dispose() {
    super.dispose();
    compositeSubscription.dispose();
  }
}

abstract class PageProvideNode extends StatelessWidget{
  final Providers mProviders = Providers();

  Widget buildContent(BuildContext context);

  @override
  Widget build(BuildContext context) {
    return ProviderNode(child: buildContent(context), providers: mProviders);
  }
}