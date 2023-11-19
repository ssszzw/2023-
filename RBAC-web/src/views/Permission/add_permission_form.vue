<template>
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle">
      <el-form
        ref="formRef"
        v-loading="formLoading"
        :model="formData"
        label-width="100px"
        :rules="rules">
        <el-form-item label="parent_id" prop="parent_id">
          <el-input v-model="formData.parent_id" placeholder="请输入parent_id" />
        </el-form-item>
        <el-form-item label="权限名称" prop="permission_name">
          <el-input v-model="formData.permission_name" placeholder="请输入权限名称" />
        </el-form-item>
        <el-form-item label="路径" prop="path">
          <el-input v-model="formData.path" placeholder="请输入路径" />
        </el-form-item>
        <el-form-item label="排名顺序号" prop="order_num">
          <el-input v-model="formData.order_num" placeholder="请输入排名顺序号" />
        </el-form-item>
        <el-form-item label="权限描述" prop="description">
          <el-input v-model="formData.description" placeholder="请输入权限描述" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="formData.status" placeholder="选择状态">
            <el-option label="ACTIVE" value="ACTIVE"></el-option>
            <el-option label="INACTIVE" value="INACTIVE"></el-option>
          </el-select>
        </el-form-item>

        <!-- 删除其他不相关的表单项 -->
      </el-form>
      <template #footer>
        <el-button :disabled="formLoading" type="primary" @click="submitForm"
          >确 定</el-button
        >
        <el-button @click="closeDialog">取 消</el-button>
      </template>
    </el-dialog>
  </template>
  
  <script>
  import * as PermissionApi from '@/api/permission';
  
  export default {
    data() {
      return {
        dialogVisible: false,
        dialogTitle: '',
        formLoading: false,
  
        formData: {
          permission_id: null,
          parent_id:null,
          permission_name: '',
          path:'',
          order_num:null,
          description: '',
          status: '',
          create_time: '',
          update_time: '',
        },
        formRef: null,
        rules: {
          parent_id: [{ required: true, message: "请输入parent_id" }],
          permission_name: [{ required: true, message: "请输入权限名", trigger: "blur" }],
          path: [{ required: true, message: "请输入path", trigger: "blur" }],
        },
      };
    },
    methods: {
      async open(type, row) {
        this.dialogVisible = true;
  
        this.dialogTitle = row ? '编辑权限':'新建权限' ;
  
        // this.dialogTitle = this.$t('action.' + type);
        this.formType = type;
        console.log('this.$refs.formRef:', this.$refs.formRef);
        await this.resetForm();
        // this.resetForm();
        
  
        // 修改时，设置数据
        if (row) {
          this.formLoading = true;
          try {
            // console.log('row-------++:',row);

            this.formData = JSON.parse(JSON.stringify(row));
            // console.log('this.formData-------++:',this.formData);

            // console.log('修改');
          } finally {
            this.formLoading = false;
          }
        }
      },
      resetForm() {
        // this.formData = {
        //     permission_id: null,
        //     parent_id:null,
        //     permission_name: '',
        //     path:'',
        //     order_num:null,
        //     description: '',
        //     status: '',
        //     create_time: '',
        //     update_time: '',
        // };
        // this.$nextTick(() => {
        //   // 确保 this.$refs.formRef 不为 null
        //   if (this.$refs.formRef) {
        //     this.$refs.formRef.resetFields();
        //     // this.$refs.formRef.clearValidate(); // 清除表单的验证状态
        //     // this.$refs.formRef.model = { ...this.formData };
        //   }
        // });
        return new Promise((resolve) => {
          this.formData = {
            permission_id: null,
            parent_id: null,
            permission_name: '',
            path: '',
            order_num: null,
            description: '',
            status: '',
            create_time: '',
            update_time: '',
          };

          this.$nextTick(() => {
            // 确保 this.$refs.formRef 不为 null
            if (this.$refs.formRef) {
              this.$refs.formRef.resetFields();
            }
            resolve();
          });
        });
      },
  
      closeDialog() {
        this.dialogVisible = false;
      },
  
      async submitForm() {
        if (!this.$refs.formRef) return;
        const valid = await this.$refs.formRef.validate();
        if (!valid) return;
        this.formLoading = true;
  
        try {
          // 调用后端保存权限的接口，传递 formData 数据
          console.log('this.formData:', this.formData);
          if (this.formType === 'create') {
            await PermissionApi.addPermission(this.formData);
            this.$message.success('添加成功');
          } else {
            await PermissionApi.editPermission(this.formData);
            this.$message.success('编辑成功');
          }
  
          this.dialogVisible = false;
          this.$emit('success');
        } catch (error) {
          console.error('添加/修改权限失败', error);
        } finally {
          this.formLoading = false;
        }
      },
    },
  };
  </script>
  