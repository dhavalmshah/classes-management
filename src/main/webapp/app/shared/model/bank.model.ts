export interface IBank {
  id?: number;
  accountName?: string;
  accountNumber?: string;
  notes?: string | null;
}

export const defaultValue: Readonly<IBank> = {};
