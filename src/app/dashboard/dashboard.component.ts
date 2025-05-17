import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dashboard-container">
      <h1>Dashboard</h1>
      <p>Bienvenido al sistema, chambeen en el front ya hicimos todo aña</p>
    </div>
  `,
  styles: [`
    .dashboard-container {
      padding: 20px;
    }
    h1 {
      color: #333;
    }
  `]
})
export class DashboardComponent {} 