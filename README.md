Booksearch
==========

A sample android application demonstrating the usage of [appbase-droid](https://github.com/appbaseio/appbase-droid) library.


Instructions
------------
The application currently uses [books dataset](https://dejavu.appbase.io/?&appname=good-books-yj&url=https://gBgUqs2tV:3456f3bf-ea9e-4ebc-9c93-08eb13e5c87c@scalr.api.appbase.io&mode=view)
indexed on an appbase app. You can run the application against your own dataset by following these steps:
1. Register and create a new app on [appbase.io](https://dashboard.appbase.io).
2. Appbase.io offers multiple tools, [abc](https://github.com/appbaseio/abc) and [dejavu](https://github.com/appbaseio/dejavu) for importing your dataset to an appbase app.
3. Note your **API Credentials** of the newly created app from the [dashboard](https://dashboard.appbase.io).
4. Replace the credentials - namely `APP_NAME`, `USERNAME` and `PASSWORD` and `SEARCH` query based on your dataset in the following class: `src/main/java/io/appbase/booksearch/constants/Constants.java`. It should look something like this:


        public final class Constants {
          public static final String URL = "https://scalr.api.appbase.io";
          public static final String APP_NAME = "<YOUR-APP-NAME>";
          public static final String TYPE = "_doc";
          public static final String USERNAME = "<YOUR-APP-USERNAME>";
          public static final String PASSWORD = "<YOUR-APP-PASSWORD>";
          public static final String SEARCH = "<YOUR-SEARCH-QUERY>";
        }


Screenshots
-----------

<img src="screenshots/book-search.gif" width="300" hspace="20">


Docs
----

Checkout the appbase-droid [repository](https://github.com/appbaseio/appbase-droid) and [docs](https://opensource.appbase.io/appbase-droid/doc/) for more examples.

License
--------
    MIT License

    Copyright (c) [year] [fullname]

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.