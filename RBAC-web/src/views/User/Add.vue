<template>
    <div class="add-user">
      <h1>添加用户Add</h1>
      <el-form ref="form" :model="user" :rules="rules" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="用户密码" prop="password">
          <el-input v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="user.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="user.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="user.phone"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script>
  import { postUser } from '@/api/login.js'
  export default {
    name: "AddUser",
    data() {
      return {
        user: {
          username: "",
          password:"",
          name: "",
          gender: "",
          phone: "",
        },
        rules: {
          username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
          password: [{ required: true, message: "请输入用户密码", trigger: "blur" }],
          name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
          gender: [{ required: true, message: "请选择性别", trigger: "change" }],
          phone: [{ required: true, message: "请输入手机号", trigger: "blur" }],
        },
        // localDialogVisible: this.dialogVisible, // 基于 prop 创建一个 data 属性  
      };
    },
    methods: {
      submitForm() {
        this.$refs.form.validate(valid => {
          if (valid) {
          // 添加用户处理逻辑中增加网络请求
            postUser(this.user)
            .then(res =>{
              if(res.code===200)
                // this.$router.push({ path: '/' })
              {//添加成功
                this.$message.success('添加成功')
                this.resetForm
                this.$emit('changeDialogVisibleAdd',false)
              }
              else
              { // 添加失败
                this.$message.error('添加失败')
              }
            })
            .catch(() => {
              // 添加失败
              this.$message.error('用户名或密码错误')
              this.loading = false
            })
            // TODO: send user data to backend
            console.log(this.user);
          } else {
            console.log("form validation failed");
          }
        });
      },
      resetForm() {
        this.$refs.form.resetFields();
      },
    },
  };
  </script>
  
  <style>
  .add-user {
    padding: 20px;
  }
  </style>
  