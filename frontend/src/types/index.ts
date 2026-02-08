export interface User {
  id: string
  username: string
  email: string
  role: 'ADMIN' | 'ANALYST' | 'VIEWER'
}

export interface Log {
  id: string
  timestamp: string
  source: string
  message: string
  level: 'INFO' | 'WARN' | 'ERROR' | 'DEBUG'
  metadata: Record<string, unknown>
}

export interface Alert {
  id: string
  timestamp: string
  severity: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL'
  message: string
  status: 'OPEN' | 'ACKNOWLEDGED' | 'RESOLVED'
  logIds: string[]
}

export interface AuditLog {
  id: string
  timestamp: string
  userId: string
  action: string
  resource: string
  status: 'SUCCESS' | 'FAILURE'
  details: Record<string, unknown>
}
