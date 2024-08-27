<script setup>
import { ref } from 'vue'

const resetPassword = ref({
  password: '',
  rePassword: '',
  confirmPassword: ''
})

// 校验密码的函数
const checkRePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次确认密码'))
  } else if (value !== resetPassword.value.rePassword) {
    callback(new Error('请确保两次输入的密码一致'))
  } else {
    callback()
  }
}

const rules = {
  password: [
    { required: true, message: '请输入原密码', trigger: 'blur' },
    { pattern: /^\S{6,10}$/, message: '密码必须是6-10位的非空字符串', trigger: 'blur'}
  ],
  rePassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { pattern: /^\S{6,10}$/, message: '新密码必须是6-10位的非空字符串', trigger: 'blur'}
  ],
  confirmPassword: [
    { pattern: /^\S{6,10}$/, message: '新密码必须是6-10位的非空字符串', trigger: 'blur'},
    { validator: checkRePassword, trigger: 'blur'}
  ]
}

import {userUpdatePasswordService} from "@/api/user.js"
import {ElMessage} from "element-plus"
import {useUserInfoStore} from "@/stores/userInfo.js"
import {useTokenStore} from "@/stores/token.js"
import router from "@/router/index.js";
const userInfoStore = useUserInfoStore()
const tokenStore = useTokenStore()

// 修改密码
const updatePassword =async ()=>{
  // 调用函数
  const result = await userUpdatePasswordService(resetPassword.value)
  ElMessage.success(result.msg?result.msg:'修改成功')

  // 清除 Pinia 中的信息 和 token
  userInfoStore.removeInfo()
  tokenStore.removeToken()

  // 退出到登录页面
  await router.push('/login')
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>重置密码</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form :model="resetPassword" :rules="rules" label-width="100px" size="large">
          <el-form-item label="原密码" prop="password">
            <el-input v-model="resetPassword.password"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="rePassword">
            <el-input v-model="resetPassword.rePassword" show-password></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="resetPassword.confirmPassword" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePassword">修改密码</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>