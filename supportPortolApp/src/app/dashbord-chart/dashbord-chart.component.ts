import { Component, OnInit } from '@angular/core';

import { Chart, registerables } from 'chart.js';
import { UserService } from '../service/user.service';
import { LigneProdctionService } from '../service/ligneProdction.service';
import { PicklisteService } from '../service/pickliste.service';

import { forkJoin } from 'rxjs';  
import { PosteService } from '../service/poste.service';

Chart.register(...registerables);

@Component({
  selector: 'app-user-chart',
  templateUrl: './dashbord-chart.component.html',
  styleUrls: ['./dashbord-chart.component.css']
})
export class DashbordChartComponent implements OnInit {

  sommesCadences: any[];
  totalUsers: number;
  activeUsers: number;
  inactiveUsers: number;
  notLockedUsers: number;
  lockedUsers: number;
  
  picklistesServi: number;
  picklistesDemander: number;
  totalPostes : number;

  lineChartData: any[] = [];
  lineChartLabels: string[] = [];
  lineChartOptions: any = {
    responsive: true,
    scales: {
      y: {
        ticks: {
          beginAtZero: true,
          precision: 0,
          stepSize: 1
        }
      }
    }
  };
 
  chart: Chart<'pie', number[], string>;

  constructor(private userService: UserService , private ligneProductionService: LigneProdctionService ,
              private picklisteService : PicklisteService  , private  posteService : PosteService) { }

  ngOnInit(): void {

    forkJoin([
      this.ligneProductionService.getSommesCadences(),
      this.userService.getTotalUsers(),
      this.userService.getActiveUsers(),
      this.userService.getInactiveUsers(),
      this.userService.getNotLockedUsersCount(),
      this.userService.getLockedUsersCount(),
      this.picklisteService.getNombrePicklistesDemander(),
      this.picklisteService.getNombrePicklistesServi(),
      this.posteService.getTotalPostes(),
    ]).subscribe(([sommesCadences, totalUsers, activeUsers, inactiveUsers , notLockedUsers ,lockedUsers, picklistesServi,
                  picklistesDemander, totalPostes]) => {
      this.sommesCadences = sommesCadences;
      this.totalUsers = totalUsers;
      this.activeUsers = activeUsers;
      this.inactiveUsers = inactiveUsers;
      this.notLockedUsers = notLockedUsers;
      this.lockedUsers = lockedUsers;
      this.picklistesServi = picklistesServi ;
      this.picklistesDemander = picklistesDemander ;
      this.totalPostes = totalPostes ;
      this.prepareChartData();
      this.createChart();
    });
  }

  prepareChartData(): void {
    if (this.sommesCadences) {
      this.lineChartLabels = this.sommesCadences.map(item => item.ligneProduction);
      const data = this.sommesCadences.map(item => item.sommeCadences);
  
      // Générer un tableau de couleurs différentes pour chaque barre de ligne de production
      const backgroundColors = this.generateRandomColors(this.sommesCadences.length);
      
      this.lineChartData = [{
        data: data,
        label: 'Somme des Cadences',
        backgroundColor: backgroundColors ,
      }];
    } else {
      this.lineChartLabels = [];
      this.lineChartData = [];
    }
  }
  
  generateRandomColors(count: number): string[] {
    const colors: string[] = [];
    for (let i = 0; i < count; i++) {
      const color = this.getRandomColor();
      colors.push(color);
    }
    return colors;
  }
  
  getRandomColor(): string {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }

  createChart() {
    if ( this.activeUsers && this.inactiveUsers  && this.notLockedUsers  && this.lockedUsers) {
      this.chart = new Chart('usersChart', {
        type: 'pie',
        data: {
          labels: [ 'Active Users', 'Inactive Users', 'Not Locked Users', 'Locked Users' ],
          datasets: [
            {
              data: [ this.activeUsers, this.inactiveUsers , this.notLockedUsers , this.lockedUsers],
              backgroundColor: ['#005CFC', '#B8B8B5', '#17C821', '#E80C0C']
            }
          ]
        },
        options: {
          plugins: {
            title: {
              display: true,
              text: 'User Chart'
            }
          }
        }
      });
    }
  }

}