//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
    wx.showModal({
      title: '温馨提示',
      content: '看完一条后，下拉可刷新内容哦~',
      confirmText: "好的",
      showCancel: false,
    })
  },
  globalData: {
    userInfo: null
  },

  refreshContent: function (that) {
    wx.showNavigationBarLoading() //在标题栏中显示加载动画
    wx.request({
      url: 'https://wangdengkang.com/api/v1/portal/random-joke',
      data: {
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        var arr = [];
        arr = res.data.result.data;
        console.log(arr[0].content);
        that.setData({ motto: arr[0].content })
      },
      fail: function () {
        wx.showModal({
          content: '您的网络异常，请检查后刷新重试~',
          confirmText: "好的",
          showCancel: false,
        })
      },
      complete: function () {
        wx.hideNavigationBarLoading()
        wx.stopPullDownRefresh()
      }
    })
  }
})