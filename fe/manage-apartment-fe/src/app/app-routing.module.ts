// angular import
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Project import
import { AdminComponent } from './theme/layouts/admin-layout/admin-layout.component';
import { GuestComponent } from './theme/layouts/guest/guest.component';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: '',
        redirectTo: '/dashboard/default',
        pathMatch: 'full'
      },
      {
        path: 'dashboard/default',
        loadComponent: () => import('./demo/default/dashboard/dashboard.component').then((c) => c.DefaultComponent)
      },
      {
        path: 'typography',
        loadComponent: () => import('./demo/ui-component/typography/typography.component')
      },
      {
        path: 'color',
        loadComponent: () => import('./demo/ui-component/ui-color/ui-color.component')
      },
      {
        path: 'sample-page',
        loadComponent: () => import('./demo/other/sample-page/sample-page.component')
      },
      {
        path: 'manage-resident',
        loadComponent: () => import('./demo/other/manage-resident/manage-resident.component').then(m => m.ManageResidentComponent)
      },
      {
        path: 'manage-resident-add',
        loadComponent: () => import('./demo/other/manage-resident-add/manage-resident-add.component')
      },
      {
        path: 'manage-resident-edit/:id',
        loadComponent: () => import('./demo/other/manage-resident-edit/manage-resident-edit.component').then(m => m.EditResidentComponent)
      },
      {
        path: 'manage-apartment',
        loadComponent: () => import('./demo/other/manage-apartment/manage-apartment.component')
      },
      {
        path: 'manage-apartment-add',
        loadComponent: () => import('./demo/other/manage-apartment-add/manage-apartment-add.component')
      },
      {
        path: 'manage-apartment-edit/:id',
        loadComponent: () => import('./demo/other/manage-apartment-edit/manage-apartment-edit.component').then(m => m.EditResidentComponent)
      },
    ]
  },
  {
    path: '',
    component: GuestComponent,
    children: [
      {
        path: 'login',
        loadComponent: () => import('./demo/authentication/login/login.component')
      },
      {
        path: 'register',
        loadComponent: () => import('./demo/authentication/register/register.component')
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
