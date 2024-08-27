// 导入request.js请求工具
import request from '@/utils/request.js'

// 提供调用注册接口的函数
export const userRegisterService = (registerData)=>{ // x-www-form-url
    const params = new URLSearchParams() // 类似于 Map<String,Object> 如: {username:'john', password:'123456'}
    for (let key in registerData){
        params.append(key,registerData[key]); // params : username=john,password=123456
    }
    return request.post('/user/register', params);
}

// 提供调用登录接口的函数
export const userLoginService = (loginData)=>{
    const params = new URLSearchParams()
    for (let key in loginData){
        params.append(key,loginData[key]);
    }
    return request.post('/user/login', params)
}

// 获取用户详细信息
export const userInfoService = ()=>{
    return request.get('/user/userInfo')
}

// 修改个人信息
export const userInfoUpdateService = (userInfoData)=>{
    return request.put('/user/update',userInfoData)
}

// 修改头像
export const userUpdateAvatarService = (avatarUrl)=>{
    const params = new URLSearchParams()
    params.append('avatarUrl',avatarUrl)
    return request.patch('/user/updateAvatar',params)
}

// 修改密码
export const userUpdatePasswordService = (resetPassword)=>{
    return request.patch('/user/updatePwd',resetPassword)
}