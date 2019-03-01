import 'package:flutter/material.dart';
import 'constant/Constant.dart';


void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: Constants.APP_NAME,
      theme: new ThemeData(
       
        primarySwatch: Colors.blue,
      ),
      home: new MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);

  

  

  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
 

  @override
  Widget build(BuildContext context) {
    
    return new Scaffold(
      appBar: AppBar(
        title: Text(Constants.APP_NAME),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            Text("简悦音乐")
          ],
        ),
      ),
    );
  }
}
